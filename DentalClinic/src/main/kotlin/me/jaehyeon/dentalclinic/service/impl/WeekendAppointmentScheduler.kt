package me.jaehyeon.dentalclinic.service.impl

import me.jaehyeon.dentalclinic.service.AppointmentScheduler
import org.springframework.stereotype.Service

@Service("weekendScheduler")
class WeekendAppointmentScheduler : AppointmentScheduler {
    override fun successMessage(): String = "Scheduled on a weekend!"
}
