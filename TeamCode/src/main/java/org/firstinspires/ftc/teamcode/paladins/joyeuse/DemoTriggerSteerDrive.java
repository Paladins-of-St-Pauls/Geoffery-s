package org.firstinspires.ftc.teamcode.paladins.joyeuse;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.paladins.common.PaladinsComponent;
import org.firstinspires.ftc.teamcode.paladins.common.PaladinsOpMode;

/**
 * Created by Shaun on 2/07/2017.
 */

public class DemoTriggerSteerDrive extends PaladinsComponent {

    private static float[] power_curve =
            {0.00f, 0.2f, 0.25f, 0.3f, 0.35f, 0.4f, 0.5f, 1.0f};
    private static float[] steer_curve =
            {0.00f, 0.2f, 0.25f, 0.3f, 0.35f, 0.4f, 0.5f, 1.0f};
    final private JoyeuseDrive drive;
    final private Gamepad gamepad;
    final private Telemetry.Item leftPowerItem;
    final private Telemetry.Item rightPowerItem;
    final private Telemetry.Item steerPowerItem;
    final private Telemetry.Item rawPowerItem;

    public DemoTriggerSteerDrive(PaladinsOpMode opMode, Gamepad gamepad, JoyeuseDrive drive) {
        super(opMode);
this.drive = drive;
        this.gamepad = gamepad;

        leftPowerItem = getOpMode().telemetry.addData("Left power", "%.2f", 0.0f);
        leftPowerItem.setRetained(true);
        rightPowerItem = getOpMode().telemetry.addData("Right power", "%.2f", 0.0f);
        rightPowerItem.setRetained(true);
        steerPowerItem = getOpMode().telemetry.addData("steer power", "%.2f", 0.0f);
        steerPowerItem.setRetained(true);
        rawPowerItem = getOpMode().telemetry.addData("raw power", "%.2f", 0.0f);
        rawPowerItem.setRetained(true);
    }

    /*
     * Update the motor power based on the gamepad state
     */
    public void update() {

//        float scalePower = scaleTriggerPower(gamepad.left_trigger - gamepad.right_trigger);

        float scalePower = scaleTriggerPower(0);

        if (gamepad.left_bumper) {
            scalePower = scaleTriggerPower(0.2f);
        }

        if (gamepad.left_trigger > 0) {
            scalePower = scaleTriggerPower((float) (gamepad.left_trigger * 0.75));
        }

        if (gamepad.right_bumper) {
            scalePower = scaleTriggerPower(-0.2f);
        }

        if (gamepad.right_trigger > 0) {
            scalePower = scaleTriggerPower((float) (-gamepad.right_trigger*0.75));
        }

        float steer = scaleSteerPower(-gamepad.left_stick_x);
        float leftPower;
        float rightPower;
        if (scalePower == 0.0f) {
            leftPower = steer;
            rightPower = -steer;
        } else {
            leftPower = scalePower * ((steer < 0) ? 1.0f - steer : 1.0f);
            rightPower = scalePower * ((steer > 0) ? 1.0f + steer : 1.0f);
        }


        if(gamepad.y) {
            float temp = leftPower;
            leftPower = -rightPower;
            rightPower = -temp;
        }

        drive.setPower(leftPower, rightPower);
        drive.update();

        leftPowerItem.setValue("%.2f", leftPower);
        rightPowerItem.setValue("%.2f", rightPower);
        steerPowerItem.setValue("%.2f", steer);
        rawPowerItem.setValue("%.2f", scalePower);
    }

    /**
     * The  DC motors are scaled to make it easier to control them at slower speeds
     * The clip method guarantees the value never exceeds the range 0-1.
     */
    private float scaleTriggerPower(float power) {

        // Ensure the values are legal.
        float clipped_power = Range.clip(power, -1, 1);

        // Remember if this is positive or negative
        float sign = Math.signum(clipped_power);

        // Work only with positive numbers for simplicity
        float abs_power = Math.abs(clipped_power);

        // Map the power value [0..1.0] to a power curve index
        int index = (int) (abs_power * (power_curve.length - 1));

        float scaled_power = sign * power_curve[index];

        return scaled_power;
    }

    private float scaleSteerPower(float p_power) {

        // Ensure the values are legal.
        float clipped_power = Range.clip(p_power, -1, 1);

        // Remember if this is positive or negative
        float sign = Math.signum(clipped_power);

        // Work only with positive numbers for simplicity
        float abs_power = Math.abs(clipped_power);

        // Map the power value [0..1.0] to a power curve index
        int index = (int) (abs_power * (steer_curve.length - 1));

        float scaled_power = sign * steer_curve[index];

        return scaled_power;

    }

}
