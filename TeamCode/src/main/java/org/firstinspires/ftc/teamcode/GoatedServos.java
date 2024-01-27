package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
public class GoatedServos extends LinearOpMode {
    private Servo yourServo; // Replace with your servo instance
    private double initialPosition = 0.0; // Initial servo position
    private double targetPosition = 1.0; // Target servo position
    private double easingFactor = 0.05; // Adjust this for the easing effect

    private  Servo rightServo;

    public void runOpMode() {
        rightServo = hardwareMap.servo.get("rightServo"); // Replace "yourServo" with your servo name
        yourServo.setPosition(initialPosition);

        waitForStart();

        while (opModeIsActive()) {
            // Call easing function to smoothly move the servo
            easeServo(targetPosition);

            telemetry.update();
        }
    }

    private void easeServo(double target) {
        double currentPosition = yourServo.getPosition();
        double nextPosition = lerp(currentPosition, target, easingFactor);
        yourServo.setPosition(nextPosition);
    }

    private double lerp(double start, double end, double t) {
        return start + t * (end - start);
    }

}
