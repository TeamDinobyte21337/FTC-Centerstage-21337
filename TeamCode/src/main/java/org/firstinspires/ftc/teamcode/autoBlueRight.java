package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

// This is an example of how to use encoders
// Encoders are a way to treat motors like servos
// Its a bit more precise than regular power
// We use encoders to send motors a certain DISTANCE rather than a certain TIME

@Autonomous

public class autoBlueRight extends LinearOpMode{ // first bracket
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

    // claw servo start
    Servo rightServo;
    // claw servo end

    // encoder defining for slider start
    double sliderTicks = 1425.1;
    double sliderTarget;
    // encoder defining for slider end

    // encoder defining for arm start
    double armTicks = 860.32;
    double armTarget;
    // encoder defining for arm end


    public void runOpMode() {

        // wheel motor mapping start
        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
        // wheel motor mapping ending

        // slider motor mapping start
        horizontalMotor = hardwareMap.dcMotor.get("horizontalMotor");
        horizontalMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        horizontalMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // slider motor mapping end

        // arm motor mapping start
        verticalMotor = hardwareMap.dcMotor.get("verticalMotor");
        verticalMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        verticalMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // claw servo mapping start
        rightServo = hardwareMap.servo.get("rightServo");
        // claw servo mapping end

        // on startup we execute the code below this
        waitForStart();



        // close claw
        rightServo.setPosition(.4);
        sleep(500);

        //move
        encoderMovement(2200, 0,0,.5);
        encoderMovement(0, 4300,0,.5);
    }


// Thats a lot of code, so lets turn this into a function so it becomes one line
        // We need these parameters to determine whether or not we are going forward, strafing, or rotating
        public void encoderMovement(int forward, int strafe, int turn, double power){

            // This is still needed from earlier
            frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            // We have entered three parameters into our position, but really we are going to set all but one of them to zero
            frontLeftMotor.setTargetPosition(-forward + strafe - turn);
            frontRightMotor.setTargetPosition(forward + strafe - turn);
            backLeftMotor.setTargetPosition(- forward - strafe - turn);
            backRightMotor.setTargetPosition( forward - strafe - turn);

            // setting the power to whatever we input into the function
            frontLeftMotor.setPower(power);
            frontRightMotor.setPower(power);
            backLeftMotor.setPower(power);
            backRightMotor.setPower(power);


            frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);


            while (frontLeftMotor.isBusy() && frontRightMotor.isBusy() && backLeftMotor.isBusy() && backRightMotor.isBusy()){

            }

            // we add a delay based off of our ms parameter
            sleep(50);
        }


    // ticks loop for slider start
    public void slider (double turnage) {
        sliderTarget = sliderTicks*turnage;
        horizontalMotor.setTargetPosition((int)sliderTarget);
        horizontalMotor.setPower(.85);
        horizontalMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (horizontalMotor.isBusy()){

        }
        sleep(50);

    }

    // ticks loop for slider end

    // ticks loop for arm start
    public void max (double turnage) {
        armTarget = armTicks*turnage;
        verticalMotor.setTargetPosition((int)armTarget);
        verticalMotor.setPower(1);
        verticalMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (verticalMotor.isBusy()){

        }
        sleep(50);

    }

    public void begin(){
        verticalMotor.setTargetPosition(0);
        verticalMotor.setPower(1);
        verticalMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (verticalMotor.isBusy()){

        }
        sleep(50);
    }
    // ticks loop for arm end



} // last bracket



