package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

@Autonomous

public class goatedAutoBlueLeft extends LinearOpMode{ // first bracket
    // wheel motor starts
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;
    // wheel motor ends

    // arm and slider motor start
    private DcMotor horizontalMotor;
    private DcMotor verticalMotor;
    // arm and slider motor end

    // claw servo start
    private  Servo rightServo;
    // claw servo end

    // encoder defining for slider start
    private   double sliderTicks = 1425.1;
    private double sliderTarget;
    // encoder defining for slider end

    // encoder defining for arm start
    private    double armTicks = 860.32;
    private   double armTarget;
    // encoder defining for arm end
    private double i;

 /**
     * The position of our object
     */
    private double horizontalPos = -100000;


    /**
     * The threshold for our object to either be on the left or the right side
     */
    private final double THRESHOLD = 270;

    /**
     * The variable to store our instance of the TensorFlow Object Detection processor.
     */
    private TfodProcessor tfod;

    /**
     * The variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;

    /**
     * Variable to store the confidence of our model
     */
    private double confidence = 0;

    /**
     * Variable to store the number of recognitions
     */
    private int numRecognitions = 0;

    /**
     * The labels we are looking for
     */
    private static final String[] LABELS = {"Prop"};

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

        // Call this function to set up our tfod processor and VisionPortal
        // initTfod();

        // While the opMode is not active, we will scan for objects
        while (!opModeIsActive()){
         //   scanForObjects();
        }


        // on startup we execute the code below this

        waitForStart();


        // The accuracy of our blue model is much higher than that of our red model
        // Because of this, we are able use the position of the object for our logic

        // If no object is detected, then we assume its the object on the left
        if (horizontalPos == -100000 || confidence < .9){

            // TODO: test this code and adjust these values
            // do something if object on left is detected

            // Strafe towards the spike
       //     encoderMovement(0,-850,0, 1, 500);

            // Move forward to the spike and place the pixel
       //     encoderMovement(-900, 0, 0, 1, 500);
            rightServo.setPosition(.75);
            sleep(500);

            // move backwards to align with the wall
      //      encoderMovement(900, 0, 0, 1, 500);

            // Strafe towards the backstage
         //   encoderMovement(0, -1300, 0, 1, 1000);
        }
        // if our object is on the left side of our threshold, then our object is in the center
        else if (horizontalPos < THRESHOLD){
            // TODO: take the values from here and paste them into other files
            // do something if object is in the center

            // Move forward and place the object on the spike
            encoderMovement(-1250, 0, 0, 1);
            rightServo.setPosition(.75);
            sleep(500);

            // Move back to the starting position
            encoderMovement(1250, 0, 0,1);

            // Move to the backstage
            encoderMovement(0, -2100, 0, 1);
        }
        // Otherwise, if our object is on the right side of our threshold, then it must be on the right spike
        else if (horizontalPos > THRESHOLD) {
            // TODO: Test the values here to ensure accuracy
            // do something if object is on the right

            // move forward to the square with all the spikes
            encoderMovement(-1200, 0, 0, 1);

            // Turn towards the proper spike
            encoderMovement(0,0,950, 1);

            // Move forward and drop our pixel

            rightServo.setPosition(.75);
            sleep(500);

            // Basically the inverse of all our movements to move back to beginning

            encoderMovement(300, 0, 0, 1);
            encoderMovement(0, 0, -950, 1);

            encoderMovement(1200, 0, 0, 1);

            // Move to the backstage to park
            encoderMovement(0, -1900, 0, 1);

        }


 // move(0, -2100, 0, 1, 1000);
//        claw.setPosition(.72);


        sleep(29000);

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
        frontLeftMotor.setTargetPosition(- (+ forward - strafe + turn));
        frontRightMotor.setTargetPosition(forward + strafe - turn);
        backLeftMotor.setTargetPosition(-forward - strafe - turn);
        backRightMotor.setTargetPosition( + forward - strafe - turn);

        // setting the power to whatever we input into the function
        frontLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        backLeftMotor.setPower(power);
        backRightMotor.setPower(power);


        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        while (backLeftMotor.isBusy() || backRightMotor.isBusy() || frontLeftMotor.isBusy() || frontRightMotor.isBusy()){
            telemetry.addLine(String.valueOf(backLeftMotor.getCurrentPosition()));
            telemetry.addLine(String.valueOf(backRightMotor.getCurrentPosition()));
            telemetry.addLine(String.valueOf(frontLeftMotor.getCurrentPosition()));
            telemetry.addLine(String.valueOf(frontRightMotor.getCurrentPosition()));
            telemetry.addLine(String.valueOf(horizontalMotor.getCurrentPosition()));
            telemetry.update();
        }

        // we add a delay based off of our ms parameter
        sleep(150);
    }

    private void initTfod() {

        // Create the TensorFlow processor by using a builder.
        tfod = new TfodProcessor.Builder()

                // Use setModelAssetName() if the TF Model is built in as an asset.
                // Use setModelFileName() if you have downloaded a custom team model to the Robot Controller.
                //.setModelAssetName(TFOD_MODEL_ASSET)
                .setModelFileName("blue.tflite")

                .setMaxNumRecognitions(1)
                .setTrackerMaxOverlap(0.25f)
                .setModelLabels(LABELS)
                .setNumDetectorThreads(1)
                .setNumExecutorThreads(1)
//            .setIsModelTensorFlow2(true)
                //.setIsModelQuantized(true)
                //.setModelInputSize(300)
                //.setModelAspectRatio(16.0 / 9.0)

                .build();

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Set the camera (webcam vs. built-in RC phone camera).

        builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));


        // Choose a camera resolution. Not all cameras support all resolutions.
        //builder.setCameraResolution(new Size(640, 480));

        // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
        //builder.enableCameraMonitoring(true);

        // Set the stream format; MJPEG uses less bandwidth than default YUY2.
        //builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);

        // Choose whether or not LiveView stops if no processors are enabled.
        // If set "true", monitor shows solid orange screen if no processors enabled.
        // If set "false", monitor shows camera view without annotations.
        //builder.setAutoStopLiveView(false);

        // Set and enable the processor.
        builder.addProcessor(tfod);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

        // Set confidence threshold for TFOD recognitions, at any time.
        //tfod.setMinResultConfidence(0.75f);

        // Disable or re-enable the TFOD processor at any time.
        //visionPortal.setProcessorEnabled(tfod, true);

    }   // end method initTfod()



    /**
     * Function that scans for objects and assigns values to variables to use for decision making
     * @return Returns true if an object is found, returns false otherwise. Mainly used for breaking out of the loop
     */
    private boolean scanForObjects(){
        List<Recognition> currentRecognitions = tfod.getRecognitions();
        telemetry.addData("# Objects Detected", currentRecognitions.size());

        numRecognitions = currentRecognitions.size();

        if (currentRecognitions.isEmpty()){

            horizontalPos = -100000;

            return false;
        }

        // Step through the list of recognitions and display info for each one.
        for (Recognition recognition : currentRecognitions) {
            horizontalPos = (recognition.getLeft() + recognition.getRight()) / 2 ;
            confidence = recognition.getConfidence();

            telemetry.addData(""," ");
            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
            telemetry.addData("- Position", "%.0f", horizontalPos);
            telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
            break;
        }   // end for() loop

        return true;

    }

    // ticks loop for slider start
    public void slider (double turnage) {
        sliderTarget = -sliderTicks*turnage;
        horizontalMotor.setTargetPosition((int) sliderTarget);
        horizontalMotor.setPower(.85);
        horizontalMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        i = 0;
        while (horizontalMotor.isBusy() && i<1000){
            i=i+1;
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

