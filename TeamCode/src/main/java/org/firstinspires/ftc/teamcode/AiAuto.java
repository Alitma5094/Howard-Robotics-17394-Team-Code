package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

@Autonomous()
public class AiAuto extends LinearOpMode {
    private static final boolean USE_WEBCAM = false;  // true for webcam, false for phone camera

    /**
     * The variable to store our instance of the TensorFlow Object Detection processor.
     */
    private TfodProcessor tfod;
    private double rotateSpeed = 0.5;
    private double moveSpeed = 0.5;

    private Robot robot = new Robot(this);

    /**
     * The variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;

    @Override
    public void runOpMode() {

        initTfod();

        // Wait for the DS start button to be touched.
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();

        // Move Forward
        robot.moveByDistance(12, moveSpeed);


        // Scan right spike mark
        if (pixelIsPresent()) {
            telemetry.addData("Pixel is found: ", "right");
            dropPixel();
        } else {
            // Rotate 90 degrees left
            robot.rotateBySeconds(5, rotateSpeed, this::sleep, "left");
            if (pixelIsPresent()) {
                telemetry.addData("Pixel is found: ", "front");
                dropPixel();
                // Rotate 90 degrees right
                robot.rotateBySeconds(5, rotateSpeed, this::sleep, "right");

            } else {
                // Rotate 180 degrees right
                robot.rotateBySeconds(10, rotateSpeed, this::sleep, "right");
                dropPixel();
                robot.rotateBySeconds(5, rotateSpeed, this::sleep, "left");

            }
        }


        visionPortal.close();


    }   // end runOpMode()

    /**
     * Initialize the TensorFlow Object Detection processor.
     */
    private void initTfod() {

        // Create the TensorFlow processor the easy way.
        tfod = TfodProcessor.easyCreateWithDefaults();

        // Create the vision portal the easy way.
        if (USE_WEBCAM) {
            visionPortal = VisionPortal.easyCreateWithDefaults(
                    hardwareMap.get(WebcamName.class, "Webcam 1"), tfod);
        } else {
            visionPortal = VisionPortal.easyCreateWithDefaults(
                    BuiltinCameraDirection.BACK, tfod);
        }

    }   // end method initTfod()

    /**
     * Add telemetry about TensorFlow Object Detection (TFOD) recognitions.
     */
    private void telemetryTfod() {

        List<Recognition> currentRecognitions = tfod.getRecognitions();
        telemetry.addData("# Objects Detected", currentRecognitions.size());

        // Step through the list of recognitions and display info for each one.
        for (Recognition recognition : currentRecognitions) {
            double x = (recognition.getLeft() + recognition.getRight()) / 2;
            double y = (recognition.getTop() + recognition.getBottom()) / 2;

            telemetry.addData("", " ");
            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
            telemetry.addData("- Position", "%.0f / %.0f", x, y);
            telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
        }   // end for() loop

    }   // end method telemetryTfod()

    private boolean pixelIsPresent() {
        boolean found = false;
        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < 10000) {
            List<Recognition> currentRecognitions = tfod.getRecognitions();
            if (currentRecognitions.size() > 0) {
                found = true;
                break;
            }
        }

        return found;
    }

    private void dropPixel() {

    }


}
