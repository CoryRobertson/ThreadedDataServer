import urllib.request
import sys


# args = sys.argv
# print(args)

def main():
    args = sys.argv
    input_from_server = args[1]
    servo_number = input_from_server[0:1]
    angle = input_from_server[2:]
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
