import sys
import urllib.request
import urllib.parse
import urllib.error
from time import sleep


servo_kit_running = False

try:
    from adafruit_servokit import ServoKit
    import board
    import busio
    import adafruit_pca9685

    i2c = busio.I2C(board.SCL, board.SDA)
    hat = adafruit_pca9685.PCA9685(i2c)
    hat.frequency = 60
    led_channel = hat.channels[2]

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
    arguments = []
    angles = []

    if servo_kit_running:
        startUpAnimation()

    while 1:
        sleep(0.0125)
        angles.clear()
        try:
            # page successfully loaded
            page = urllib.request.urlopen(args[1])
            pageText = str(page.read())
            conv = str(pageText[2:pageText.rfind('\'')])
            arguments = conv.split(",")
            angles.append(arguments[0])  # arg 0 is angle 0 of servo 0
            angles.append(arguments[1])  # and so on
            change_servo_angles(angles)
        except urllib.error.URLError:
            # if we are here the web page was most likely unable to load for a brief amount of time, we can just try again
            print("web page dropped")
        # print(arguments)

        if servo_kit_running:
            if arguments[2] == "true":
                print("laser is on!")
                led_channel.duty_cycle = 65534
            else:
                print("laser is off!")
                led_channel.duty_cycle = 0



def change_servo_angles(angles):
    # angle = clamp_angle(angle)
    # servo_number = clamp_servo_selection(servo_number)
    count = 0
    for angle in angles:
        if servo_kit_running:
            print("CLAMPED servo-kit running")
            kit.servo[clamp_servo_selection(count)].angle = int(clamp_angle(angle))
        else:
            print("UNCLAMPED servo number, angle: " + str(count) + ", " + str(angle) + " ")
        count += 1
    # print("servo number to change: " + str(servo_number) + " ")
    # print("servo angle: " + str(angle))


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

def startUpAnimation():
    # pretty bad but fun manual animation for startup :)
    delay = 0.5
    sleep(delay)
    kit.servo[0].angle = 90
    kit.servo[1].angle = 90
    sleep(delay)
    kit.servo[0].angle = 180
    kit.servo[1].angle = 0
    sleep(delay*2)
    kit.servo[0].angle = 180
    kit.servo[1].angle = 180
    sleep(delay*2)
    kit.servo[0].angle = 90
    kit.servo[1].angle = 90
    sleep(delay)
    kit.servo[0].angle = 0
    kit.servo[1].angle = 180
    sleep(delay*2)
    kit.servo[0].angle = 0
    kit.servo[1].angle = 0
    sleep(delay*2)
    kit.servo[0].angle = 90
    kit.servo[1].angle = 90
    sleep(delay*4)


if __name__ == '__main__':
    main()
