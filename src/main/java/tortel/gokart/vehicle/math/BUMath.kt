package tortel.gokart.vehicle.math

import kotlin.math.*

class BUMath {
    companion object {

        //Chapter 3: Forces
        // Function to calculate aerodynamic drag force
        fun fDrag(cDrag: Double, velocity: Double): Double {
            return -cDrag * velocity * abs(velocity)
        }

        // Function to calculate rolling resistance force
        fun fRR(cRr: Double, velocity: Double): Double {
            return -cRr * velocity
        }

        // Function to calculate gravitational force component
        fun fG(mass: Double, angle: Double, gravity: Double = 9.81): Double {
            return mass * gravity * sin(angle)
        }

        // Function to calculate traction force
        fun fTraction(tWheel: Double, rWheel: Double): Double {
            return tWheel / rWheel
        }

        // Function to calculate braking force
        fun fBraking(u: Double, cBraking: Double): Double {
            return -u * cBraking
        }

        // Function to calculate total longitudinal force
        fun fLong(fTraction: Double, fDrag: Double, fRR: Double, fG: Double): Double {
            return fTraction + fDrag + fRR + fG
        }

        // Function to calculate acceleration
        fun calculateAcceleration(fLong: Double, mass: Double): Double {
            return fLong / mass
        }

        // Function to update velocity
        fun updateVelocity(v: Double, dt: Double, acceleration: Double): Double {
            return v + dt * acceleration
        }

        // Function to update position
        fun updatePosition(p: Double, dt: Double, velocity: Double): Double {
            return p + dt * velocity
        }

        //Chapter 4: Engine Torque
        // Function to calculate angular velocity from RPM
        fun calculateAngularVelocity(rpm: Int): Double {
            return (2 * PI * rpm) / 60
        }

        // Function to calculate wheel torque
        fun calculateWheelTorque(engineTorque: Double, gearRatio: Double, finalDriveRatio: Double): Double {
            return engineTorque * gearRatio * finalDriveRatio
        }

        // Function to calculate wheel angular velocity from engine RPM
        fun calculateWheelAngularVelocity(engineRpm: Int, gearRatio: Double, finalDriveRatio: Double): Double {
            return (2 * PI * engineRpm) / (60 * gearRatio * finalDriveRatio)
        }

        // Function to calculate translational velocity of the car
        fun calculateTranslationalVelocity(engineRpm: Int, gearRatio: Double, finalDriveRatio: Double, wheelRadius: Double): Double {
            // Replicate the logic of calculateWheelAngularVelocity directly here
            val wheelAngularVelocity = (2 * PI * engineRpm) / (60 * gearRatio * finalDriveRatio)
            return wheelRadius * wheelAngularVelocity
        }

        // Function to calculate cornering force
        fun fCornering(ca: Double, alpha: Double): Double {
            //Where ca is known as the cornering stiffness
            return ca * alpha
        }

        // Function to calculate slip angle for the front wheels
        fun alphaFront(vLat: Double, vLong: Double, omega: Double, b: Double, sigma: Double): Double {
            return atan((vLat + omega * b) / vLong) - sigma * sign(vLong)
        }

        // Function to calculate slip angle for the rear wheels
        fun alphaRear(vLat: Double, vLong: Double, omega: Double, c: Double): Double {
            return atan((vLat - omega * c) / vLong)
        }

        fun fLat(ca: Double, alpha: Double, load: Double, maxFrictionForce: Double, wheelSlip: Boolean): Double {
            // Step 1: Calculate initial lateral force
            var flat = ca * alpha

            // Step 2: Cap to maximum normalized friction force
            flat = min(flat, maxFrictionForce)

            // Step 3: Multiply by load
            flat *= load

            // Step 4: Adjust for wheel slip
            if (wheelSlip) {
                flat *= 0.5
            }

            return flat
        }

        fun calculateTorque(flatFront: Double, flatRear: Double, b: Double, c: Double, sigma: Double): Double {
            return cos(sigma) * flatFront * b - flatRear * c
        }

        //Chapter 6: Implementing car physics engine (part 1)

    }
}
