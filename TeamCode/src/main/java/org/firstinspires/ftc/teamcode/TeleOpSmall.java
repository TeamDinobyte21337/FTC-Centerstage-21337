package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@TeleOp (name = "TeleOpSmall")


public class TeleOpSmall extends OpMode { // first bracket
    // wheel motor starts
    DcMotor frontLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backLeftMotor;
    DcMotor backRightMotor;
    // wheel motor ends

    // arm and slider motor start
    DcMotor horizontalMotor;
    DcMotor verticalMotor;
    // arm and slider motor end

    // hook motors start
    DcMotor hookFrontRightMotor;
    DcMotor hookFrontLeftMotor;
    // hook motors start

   // claw servo start
    Servo leftServo;
    Servo rightServo;
    // claw servo end

    // launcher servo start
    Servo launcherServo;
    // launcher servo end

    // encoder defining for slider start
    double sliderTicks = 1425.1;
    double sliderTarget;
    // encoder defining for slider end

    // encoder defining for arm start
    double armTicks = -537.7;
    double armTarget;
    // encoder defining for arm end

    // encoder defining for hook arm right motor start
    double hookTicksRight = 537.7;
    double hookTargetRight;
    // encoder defining for hook arm right motor end

    // encoder defining for hook arm left motor start

    double hookTicksLeft = 537.7;

    double hookTargetLeft;

    // encoder defining for hook arm left motor end

    public void init() { // second start bracket

        // wheel motor mapping start
        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
        // wheel motor mapping ending

        // slider motor mapping start
        horizontalMotor =  hardwareMap.dcMotor.get("horizontalMotor");
        horizontalMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // slider motor mapping end

        // arm motor mapping start
        verticalMotor =  hardwareMap.dcMotor.get("verticalMotor");
        verticalMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // arm motor mapping end

        // hook motor mapping start
        hookFrontRightMotor = hardwareMap.dcMotor.get("hookFrontRightMotor");
        hookFrontLeftMotor = hardwareMap.dcMotor.get("hookFrontLeftMotor");
        // hook motor mapping end

        // claw servo mapping start
        leftServo = hardwareMap.servo.get("leftServo");
        rightServo = hardwareMap.servo.get("rightServo");
        // claw servo mapping end

        // launcher servo mapping start
        launcherServo = hardwareMap.servo.get("launcherServo");
        // launcher servo mapping end

        // encoders for slider start
        horizontalMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        horizontalMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        horizontalMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        horizontalMotor.setMode(DcMotor.RunMode.RESET_ENCODERS);
        // encoders for slider end

        // encoders for arm start
        verticalMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        verticalMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        verticalMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        verticalMotor.setMode(DcMotor.RunMode.RESET_ENCODERS);
        // encoders for arm end

    } // second end bracket


    public void loop() { // start of loop

        // teleop movement defining start
        double forward = -.80*gamepad1.left_stick_y;
        double strafe = - .60*gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;
        // teleop movement defining end

        // teleop movement motor directions start
        frontLeftMotor.setPower(-forward + strafe - turn);
        frontRightMotor.setPower(forward + strafe - turn);
        backLeftMotor.setPower(- forward - strafe - turn);
        backRightMotor.setPower( forward - strafe - turn);
        // teleop movement motor directions end

        // encoder mapping buttons for slider start
        if(gamepad2.left_bumper) {
            full(1.45);
        }

        if(gamepad2.right_bumper) {
            origin();
        }

        if(gamepad2.dpad_right) {
            full(0.725);
        }

        telemetry.addData("Slider ticks: ", horizontalMotor.getCurrentPosition());
       // encoder mapping buttons for slider end


        // encoder mapping button for arm start
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

        // encoder mapping button for arm end

        // encoder mapping button for arm hooks start
        if (gamepad2.right_stick_button) {
           upForRight(1);
        }

        if (gamepad2.left_stick_button) {
           upForLeft(1);
        }
        // encoder mapping button for arm hook end


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
         if (gamepad2.y) {
             launcherServo.setPosition(1);
         }

        if (gamepad2.x) {
            launcherServo.setPosition(0);
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

    // ticks loop for front right hook start
    public void upForRight(double turnage) {
        hookTargetRight = hookTicksRight*turnage;
        hookFrontRightMotor.setTargetPosition((int)hookTargetRight);
        hookFrontRightMotor.setPower(.6);
        hookFrontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    public void upForLeft(double turnage) {
        hookTargetLeft = hookTicksLeft*turnage;
        hookFrontLeftMotor.setTargetPosition((int)hookTargetLeft);
        hookFrontLeftMotor.setPower(.6);
        hookFrontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    // ticks loop for front right hook start




} // last bracket
