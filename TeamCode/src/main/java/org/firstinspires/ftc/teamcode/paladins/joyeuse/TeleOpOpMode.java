package org.firstinspires.ftc.teamcode.paladins.joyeuse;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.paladins.common.PaladinsOpMode;

@TeleOp(name = "JoyeuseTeleOp")
public class TeleOpOpMode extends PaladinsOpMode {
    private JoyeuseConfiguration config;
    private JoyeuseDrive drive;
    private JoyeuseIntake intake;
    private JoyeuseShoot shoot;
    private JoyeuseGauntlet gauntlet;
    private JoyeuseTriggerSteerDrive steerDrive;

    private boolean intakeReverse;

    private boolean arm;
    private boolean hand;

    private boolean shooterOn;

    private boolean intakeOn;
    private boolean bumpOn;

    private boolean conveyorOn;

    private boolean gripperClosed;

    @Override
    protected void onInit() {
        config = JoyeuseConfiguration.newConfig(hardwareMap, telemetry);

        drive = new JoyeuseDrive(this, config.leftMidMotor, config.leftBackMotor, config.rightMidMotor, config.rightBackMotor);
        intake = new JoyeuseIntake(this, config.intakeMotor, config.bumpMotor, config.conveyorServo);
        shoot = new JoyeuseShoot(this, config.leftShooterMotor, config.rightShooterMotor);
        gauntlet = new JoyeuseGauntlet(this, config.wgArm, config.wgHand, config.wgGripper);
        steerDrive = new JoyeuseTriggerSteerDrive(this, gamepad1, drive);

        shooterOn = false;

        intakeOn = false;
        bumpOn = false;
        conveyorOn = false;

        gripperClosed = false;
    }

    @Override
    protected void activeLoop() throws InterruptedException {

//        DRIVER (GAMEPAD) 1 CONTROLS
        steerDrive.update();

//        if (gamepad1.dpad_up && !was1DpadUp) {
//            shooterOn ^= true;
//        }

//        if (shooterOn) {
//            shoot.setPower(1.0);
//        } else {
//            shoot.setPower(0);
//        }
//
//        if (gamepad1.dpad_left && !was1DpadLeft) {
//            intakeOn ^= true;
//        }
//
//        if (gamepad1.dpad_right && !was1DpadRight) {
//            bumpOn ^= true;
//        }
//
//        if (intakeOn) {
//            intake.setIntakePower(1.0);
//        } else {
//            intake.setIntakePower(0);
//        }
//
//        if (bumpOn) {
//            intake.setBumpPower(1.0);
//        } else {
//            intake.setBumpPower(0);
//        }

//        DRIVER (GAMEPAD) 2 CONTROLS

        boolean was2DpadUp = false;
        boolean was2DpadDown = false;
        boolean was2DpadLeft = false;
        boolean was2DpadRight = false;
        boolean was2A = false;
        boolean was2B = false;
        boolean was2X = false;
        boolean was2Y = false;

        if(gamepad2.dpad_up && !gamepad2.dpad_down && !was2DpadUp) {
            intakeReverse = false;
        }

        if(gamepad2.dpad_down && !gamepad2.dpad_up && !was2DpadDown) {
            intakeReverse = true;
        }

        if(intakeReverse) {
            telemetry.addLine("Intake Mode: REVERSE");
        } else {
            telemetry.addLine("Intake Mode: FORWARD");
        }

        if(gamepad2.y && !was2Y) {
            intakeOn ^= true;
        }

        if(gamepad2.b && !was2B) {
            bumpOn ^= true;
        }

        if(gamepad2.a && !was2A) {
            conveyorOn ^= true;
        }

        if(gamepad2.x && !was2X) {
            shooterOn ^= true;
        }

        if(shooterOn) {
            if(intakeReverse) {
                shoot.setPower(-1.0);
            } else {
                shoot.setPower(1.0);
            }
        } else {
            shoot.setPower(0);
        }

        if(intakeOn) {
            if(intakeReverse) {
                intake.setIntakePower(1.0);
            } else {
                intake.setIntakePower(-1.0);
            }
        } else {
            intake.setIntakePower(0);
        }

        if(bumpOn) {
            if(intakeReverse) {
                intake.setBumpPower(1.0);
            } else {
                intake.setBumpPower(-1.0);
            }
        } else {
            intake.setBumpPower(0);
        }

        if(conveyorOn) {
            if(intakeReverse) {
                intake.setConveyorServoPower(1.0);
            } else {
                intake.setConveyorServoPower(-1.0);
            }
        } else {
            intake.setConveyorServoPower(0);
        }

        if(gamepad2.dpad_left && !was2DpadLeft) {
            arm ^= true;
        }

        if(arm) {
            gauntlet.setArmPos(0.5);
        } else {
            gauntlet.setArmPos(1.0);
        }

        if(gamepad2.dpad_right && !was2DpadRight) {
            hand ^= true;
        }

        if(hand) {
            gauntlet.setHandPos(0);
        } else {
            gauntlet.setHandPos(1.0);
        }


        was2DpadUp = gamepad2.dpad_up;
        was2DpadDown = gamepad2.dpad_down;
        was2DpadLeft = gamepad2.dpad_left;
        was2DpadRight = gamepad2.dpad_right;
        was2A = gamepad2.a;
        was2B = gamepad2.b;
        was2X = gamepad2.x;
        was2Y = gamepad2.y;

//        gauntlet.setArmPos(gamepad2.left_stick_y);


//        config.wgArm.setPosition(gamepad2.left_trigger);
        telemetry.addData("Arm Position", config.wgArm.getPosition());
        telemetry.addData("Hand Position", config.wgHand.getPosition());

    }
}