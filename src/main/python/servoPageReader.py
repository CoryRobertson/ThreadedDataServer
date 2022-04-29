import sys
import urllib.request
import urllib.parse
from time import sleep


servo_kit_running = False

try:
    from adafruit_servokit import ServoKit

    kit = ServoKit(channels=16)
    servo_kit_running = True

except:
    print("running without servokit")


# args = sys.argv
# print(args)

def main():

    args = sys.argv
    print(args)
    # input_from_server = args[1]
    angles = []
    while 1:
        sleep(0.0125)
        try:
            #page successfully loaded
            page = urllib.request.urlopen(args[1])
            pageText = str(page.read())
            conv = str(pageText[2:pageText.rfind('\'')])
            angles = conv.split(",")
        except:
            #if we are here the web page was most likely unable to load for a brief amount of time, we can just try again
            print("web page dropped")
        #print(angles)
        change_servo_angles(angles)


def change_servo_angles(angles):
    #angle = clamp_angle(angle)
    #servo_number = clamp_servo_selection(servo_number)
    count = 0
    for angle in angles:
        if servo_kit_running:
            print("CLAMPED servo-kit running")
            kit.servo[clamp_servo_selection(count)].angle = int(clamp_angle(angle))
        else:
            print("UNCLAMPED servo number, angle: " + str(count) + ", " + str(angle) + " ")
        count+=1
    #print("servo number to change: " + str(servo_number) + " ")
    #print("servo angle: " + str(angle))



def clamp_angle(angle):
    if int(angle) > 180:
        return 180
    if int(angle) < 0:
        return 0
    return angle


def clamp_servo_selection(servo_number):
    if int(servo_number) >= 16:
        return 16
    if int(servo_number) <= 0:
        return 0
    return servo_number


if __name__ == '__main__':
    main()
