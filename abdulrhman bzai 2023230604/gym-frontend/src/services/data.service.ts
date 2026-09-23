import type { MemberFormModel, MemberModel } from "@/models/member.model"
import type { PlanModel } from "@/models/plan.model"
import type { TrainerModel } from "@/models/trainer.model"
import axios from "axios"

export const API_BASE = 'http://localhost:7000/api'

const client = axios.create({
    baseURL: API_BASE,
    headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
    }
})

export class DataService {

    // ---------- Membership plans (price list) ----------

    static async getPlans() {
        return client.get<PlanModel[]>('/plan')
    }

    static async getPlanById(id: number) {
        return client.get<PlanModel>(`/plan/${id}`)
    }

    static async createPlan(plan: Partial<PlanModel>) {
        return client.post<PlanModel>('/plan', plan)
    }

    static async updatePlan(id: number, plan: Partial<PlanModel>) {
        return client.put<PlanModel>(`/plan/${id}`, plan)
    }

    static async deletePlan(id: number) {
        return client.delete(`/plan/${id}`)
    }

    // ---------- Trainers ----------

    static async getTrainers() {
        return client.get<TrainerModel[]>('/trainer')
    }

    static async getTrainerById(id: number) {
        return client.get<TrainerModel>(`/trainer/${id}`)
    }

    static async createTrainer(trainer: Partial<TrainerModel>) {
        return client.post<TrainerModel>('/trainer', trainer)
    }

    static async updateTrainer(id: number, trainer: Partial<TrainerModel>) {
        return client.put<TrainerModel>(`/trainer/${id}`, trainer)
    }

    static async deleteTrainer(id: number) {
        return client.delete(`/trainer/${id}`)
    }

    // ---------- Members ----------

    static async getMembers() {
        return client.get<MemberModel[]>('/member')
    }

    static async getMemberById(id: number) {
        return client.get<MemberModel>(`/member/${id}`)
    }

    static async createMember(member: MemberFormModel) {
        return client.post<MemberModel>('/member', member)
    }

    static async updateMember(id: number, member: MemberFormModel) {
        return client.put<MemberModel>(`/member/${id}`, member)
    }

    static async deleteMember(id: number) {
        return client.delete(`/member/${id}`)
    }
}
