import type { PlanModel } from "./plan.model"
import type { TrainerModel } from "./trainer.model"

export type MemberStatus = 'Active' | 'Expiring soon' | 'Expired'

// What the backend returns: a member together with its plan and trainer (the relations)
export interface MemberModel {
    id: number
    name: string
    email: string
    plan: PlanModel
    trainer: TrainerModel | null   // null = trains alone
    startDate: string
    endDate: string
    totalPrice: number
    status: MemberStatus
    createdAt: string
    updatedAt: string | null
}

// What we send to the backend when creating/updating a member
export interface MemberFormModel {
    name: string
    email: string
    startDate: string
    planId: number | null
    trainerId: number | null
}
