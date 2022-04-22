import sys

servokit_running = False

try:
    from adafruit_servokit import ServoKit
    kit = ServoKit(channels=16)
    servokit_running = True

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
    print("servo number to change: " + str(servo_number) + " ")
    print("servo angle: " + str(angle))


if __name__ == '__main__':
    main()
