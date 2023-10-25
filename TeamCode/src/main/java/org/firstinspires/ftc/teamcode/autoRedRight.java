package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

// This is an example of how to use encoders
// Encoders are a way to treat motors like servos
// Its a bit more precise than regular power
// We use encoders to send motors a certain DISTANCE rather than a certain TIME

@Autonomous

public class autoRedRight extends LinearOpMode{ // first bracket
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
        // slider motor mapping end

        // arm motor mapping start
        verticalMotor = hardwareMap.dcMotor.get("verticalMotor");
        verticalMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // on startup we execute the code below this
        waitForStart();

        encoderMovement(400, 0,0,.5);
        encoderMovement(0, -2000,0,.5);
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






} // last bracket
