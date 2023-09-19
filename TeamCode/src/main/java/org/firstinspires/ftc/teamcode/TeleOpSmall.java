package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@TeleOp (name = "TeleOpSmall")


public class TeleOpSmall extends OpMode { // first bracket

    DcMotor frontLeftMotor;

    DcMotor frontRightMotor;

    DcMotor backLeftMotor;

    DcMotor backRightMotor;

    DcMotor horizontalMotor;

    DcMotor verticalMotor;

    Servo leftServo;

    Servo rightServo;



    public void init() { // second start bracket

        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
        horizontalMotor =  hardwareMap.dcMotor.get("horizontalMotor");
        horizontalMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        verticalMotor =  hardwareMap.dcMotor.get("verticalMotor");
        verticalMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftServo = hardwareMap.servo.get("leftServo");
        rightServo = hardwareMap.servo.get("rightServo");
    } // second end bracket


    public void loop() { // third start bracket

        double forward = -.80*gamepad1.left_stick_y;
        double strafe = - .60*gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;
        double arm = .65*gamepad2.left_stick_y;
        double arm2 = .85*gamepad2.right_stick_y;


        frontLeftMotor.setPower(-forward + strafe - turn);
        frontRightMotor.setPower(forward + strafe - turn);
        backLeftMotor.setPower(- forward - strafe - turn);
        backRightMotor.setPower( forward - strafe - turn);
        horizontalMotor.setPower(arm2);
        verticalMotor.setPower(-arm);



        if (gamepad2.a){
            leftServo.setPosition(1);
            rightServo.setPosition(0);
        }
        if (gamepad2.b){
            leftServo.setPosition(.83);
            rightServo.setPosition(.17);
        }



    } // third end bracket


} // last bracket
