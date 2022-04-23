import sys

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
    servo_number = args[1]
    angle = args[2]
    # print(servo_number, input_from_server)
    change_servo_angle(servo_number, angle)
    # page = urllib.request.urlopen('http://localhost:8123')
    # print(page.read())
    # TODO: maybe skip the webserver and instead simply take command line args, prob way easier
    # TODO: do something based on some inputs to allow the web server read output determine something


def change_servo_angle(servo_number, angle):
    angle = clamp_angle(angle)
    servo_number = clamp_servo_selection(servo_number)
    print("servo number to change: " + str(servo_number) + " ")
    print("servo angle: " + str(angle))
    if servo_kit_running:
        print("servo-kit running")
        kit.servo[servo_number].angle = angle


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


if __name__ == '__main__':
    main()
