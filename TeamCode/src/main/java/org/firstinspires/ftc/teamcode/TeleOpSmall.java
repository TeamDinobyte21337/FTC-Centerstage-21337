package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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

    Servo launcherServo;

    double sliderTicks = 1425.1;
    double sliderTarget;

    double armTicks = -537.7;
    double armTarget;

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
        launcherServo = hardwareMap.servo.get("launcherServo");

        // endocers for slider
        horizontalMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        horizontalMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        horizontalMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        horizontalMotor.setMode(DcMotor.RunMode.RESET_ENCODERS);
        // end of encoders for slider

        // start of encoders
        verticalMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        verticalMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        verticalMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        verticalMotor.setMode(DcMotor.RunMode.RESET_ENCODERS);
        // encoders for arm


    } // second end bracket


    public void loop() { // start of loop

        double forward = -.80*gamepad1.left_stick_y;
        double strafe = - .60*gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;



        frontLeftMotor.setPower(-forward + strafe - turn);
        frontRightMotor.setPower(forward + strafe - turn);
        backLeftMotor.setPower(- forward - strafe - turn);
        backRightMotor.setPower( forward - strafe - turn);

        // enocder mapping buttons for slider
        if(gamepad2.left_bumper) {
            full(1.45);
        }

        if(gamepad2.right_bumper) {
            origin();
        }

        if(gamepad2.y) {
            full(0.725);
        }

        telemetry.addData("Slider ticks: ", horizontalMotor.getCurrentPosition());
       // encoder mapping button end for slider


        // encoder mapping button start for arm
        if(gamepad2.dpad_down) {
            max(.45);
        }

        if(gamepad2.dpad_left) {
            max(.20);
        }

        if(gamepad2.dpad_up) {
             begin();
        }

        telemetry.addData("Motor arm ticks: ", verticalMotor.getCurrentPosition());

        // // encoder mapping button end for arm


       // claw servos mapping start
        if (gamepad2.a){
            leftServo.setPosition(1);
            rightServo.setPosition(0);
        }
        if (gamepad2.b){
            leftServo.setPosition(.83);
            rightServo.setPosition(.17);
        }
      // claw servos mapping ending

      // launcher servo mapping start
         if (gamepad2.right_stick_button) {
             launcherServo.setPosition(1);
         }
      // launcher servo mapping end




    } // end of loop




    // ticks loop for slider start
     public void full (double turnage) {
        sliderTarget = sliderTicks*turnage;
         horizontalMotor.setTargetPosition((int)sliderTarget);
         horizontalMotor.setPower(.85);
         horizontalMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
     }

     public void origin(){
         horizontalMotor.setTargetPosition(0);
         horizontalMotor.setPower(0.9);
         horizontalMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
     }

     // ticks loop for slider end

    // ticks loop for arm start
    public void max (double turnage) {
        armTarget = armTicks*turnage;
        verticalMotor.setTargetPosition((int)armTarget);
        verticalMotor.setPower(.4);
        verticalMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void begin(){
        verticalMotor.setTargetPosition(0);
        verticalMotor.setPower(0.9);
        verticalMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    // ticks loop for arm end







} // last bracket
