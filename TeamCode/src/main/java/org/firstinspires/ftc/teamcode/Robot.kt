package org.firstinspires.ftc.teamcode
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo


enum class Motors {
    TopLeft,
    TopRight,
    BottomLeft,
    BottomRight,
    ArmLeft,
    ArmRight
}

enum class Servos {
    Claw,
    Wrist,
}

class Robot(private val opMode: OpMode) {
    lateinit var motors: HashMap<Motors, DcMotor>
    lateinit var servos: HashMap<Servos, Servo>

    private val hardwareMap: HardwareMap
        get() = opMode.hardwareMap

    fun init() {
        motors = hashMapOf(
            Motors.TopLeft to hardwareMap.get(DcMotor::class.java, "topLeft"),
            Motors.TopRight to hardwareMap.get(DcMotor::class.java, "topRight"),
            Motors.BottomLeft to hardwareMap.get(DcMotor::class.java, "bottomLeft"),
            Motors.BottomRight to hardwareMap.get(DcMotor::class.java, "bottomRight"),

            Motors.ArmLeft to hardwareMap.get(DcMotor::class.java, "armLeft"),
            Motors.ArmRight to hardwareMap.get(DcMotor::class.java, "armRight"),
        )

        motors[Motors.TopLeft]?.direction = DcMotorSimple.Direction.FORWARD
        motors[Motors.TopRight]?.direction = DcMotorSimple.Direction.REVERSE
        motors[Motors.BottomLeft]?.direction = DcMotorSimple.Direction.FORWARD
        motors[Motors.BottomRight]?.direction = DcMotorSimple.Direction.REVERSE

        motors[Motors.ArmLeft]?.direction = DcMotorSimple.Direction.FORWARD
        motors[Motors.ArmRight]?.direction = DcMotorSimple.Direction.FORWARD

        servos = hashMapOf(
                Servos.Wrist to hardwareMap.get(Servo::class.java, "wrist"),
                Servos.Claw to hardwareMap.get(Servo::class.java, "claw")
        )
    }

    fun setMotorsMode(mode: DcMotor.RunMode, vararg motors: Motors) {
        for (motor in motors) {
            this.motors[motor]?.mode = mode
        }
    }

    fun getMotorPos(motor: Motors): Int {
        return this.motors[motor]!!.currentPosition
    }

    fun setMotorsPower(power: Double, vararg motors: Motors) {
        for (motor in motors) {
            this.motors[motor]?.power = power
        }
    }

    fun setMotorsTargetPos(position: Double, vararg motors: Motors) {
        for (motor in motors) {
            this.motors[motor]?.targetPosition = position.toInt()
        }
    }

    fun setServoPoition(position: Double, vararg servos: Servos) {
        for (servo in servos) {
            this.servos[servo]?.position = position.toDouble()
        }
    }
    private fun isMotorBusy(motor: Motors): Boolean {
        return motors[motor]?.mode == DcMotor.RunMode.RUN_TO_POSITION && motors[motor]?.isBusy!!
    }

    private fun areAllMotorsBusy(vararg motors: Motors): Boolean {
        return motors.all { m -> isMotorBusy(m) }
    }

    fun moveByDistance(inches: Double, power: Double = DEFAULT_MOTOR_POWER) {
        val target = inches / (WHEEL_CIRCUMFERENCE / MOTOR_TICK_COUNT)

        setMotorsMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER, Motors.TopLeft, Motors.BottomLeft, Motors.TopRight, Motors.BottomRight)

        setMotorsTargetPos(-target, Motors.TopLeft, Motors.TopRight, Motors.BottomLeft, Motors.BottomRight)
        setMotorsPower(-power, Motors.TopRight, Motors.BottomRight)
        setMotorsPower(power, Motors.TopLeft, Motors.BottomLeft)
        setMotorsMode(DcMotor.RunMode.RUN_TO_POSITION, Motors.TopLeft, Motors.TopRight, Motors.BottomLeft, Motors.BottomRight)

        while (areAllMotorsBusy(Motors.TopLeft, Motors.TopRight, Motors.BottomLeft, Motors.BottomRight)) { }

        setMotorsPower(0.0, Motors.TopLeft, Motors.TopRight, Motors.BottomLeft, Motors.BottomRight)
        setMotorsMode(DcMotor.RunMode.RUN_USING_ENCODER, Motors.TopLeft, Motors.TopRight, Motors.BottomLeft, Motors.BottomRight)
    }

    companion object {
        const val DEFAULT_MOTOR_POWER = 0.5
        const val MOTOR_TICK_COUNT = 537.7
        const val WHEEL_DIAMETER = 3.78 // Inches
        const val WHEEL_CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER // Inches
    }
}