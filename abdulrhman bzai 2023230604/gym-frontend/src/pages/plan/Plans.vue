<script lang="ts" setup>
import DataCard from '@/components/DataCard.vue'
import Loading from '@/components/Loading.vue'
import type { MemberModel } from '@/models/member.model'
import type { PlanModel } from '@/models/plan.model'
import type { TrainerModel } from '@/models/trainer.model'
import { DataService } from '@/services/data.service'
import {
    formatMonths, formatPrice, getErrorMessage, planClass,
    showConfirm, showError, showSuccess
} from '@/utils'
import { onMounted, ref } from 'vue'

const plans = ref<PlanModel[]>([])
const trainers = ref<TrainerModel[]>([])
const members = ref<MemberModel[]>([])
const loading = ref(true)
const error = ref('')

function memberCount(plan: PlanModel) {
    return members.value.filter(m => m.plan.id === plan.id).length
}

async function loadData() {
    try {
        const [p, t, m] = await Promise.all([
            DataService.getPlans(),
            DataService.getTrainers(),
            DataService.getMembers()
        ])
        plans.value = p.data
        trainers.value = t.data
        members.value = m.data
    } catch (e) {
        error.value = getErrorMessage(e)
    } finally {
        loading.value = false
    }
}

async function deletePlan(plan: PlanModel) {
    const confirmed = await showConfirm(`Delete the ${plan.name} plan?`, 'Delete plan')
    if (!confirmed) return

    try {
        await DataService.deletePlan(plan.id)
        showSuccess('Plan deleted')
        loadData()
    } catch (e) {
        showError(e, 'Not deleted')
    }
}

onMounted(() => loadData())
</script>

<template>
    <DataCard title="Price list" lead="Membership plans. The longer the plan, the less each month costs."
        btn-title="Add plan" btn-to="/plans/new">
        <Loading v-if="loading" />
        <div class="alert alert-danger mb-0" v-else-if="error">{{ error }}</div>
        <div class="empty-state" v-else-if="plans.length === 0">
            No plans yet. Use "Add plan" to create the first one.
        </div>
        <table class="table align-middle mb-0" v-else>
            <thead>
                <tr>
                    <th scope="col">Plan</th>
                    <th scope="col">Duration</th>
                    <th scope="col" class="text-end">Price</th>
                    <th scope="col" class="text-end">Per month</th>
                    <th scope="col">Members</th>
                    <th scope="col" class="text-end">Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="p in plans" :key="p.id">
                    <td><span :class="planClass(p.durationMonths)">{{ p.name }}</span></td>
                    <td>{{ formatMonths(p.durationMonths) }}</td>
                    <td class="text-end fw-semibold text-nowrap">{{ formatPrice(p.price) }}</td>
                    <td class="text-end text-nowrap">{{ formatPrice(p.price / p.durationMonths) }}</td>
                    <td>{{ memberCount(p) }}</td>
                    <td class="text-end">
                        <div class="btn-group">
                            <RouterLink class="btn btn-sm btn-outline-primary" :to="`/plans/${p.id}`"
                                :aria-label="`Edit ${p.name}`">
                                <i class="fa-solid fa-pen-to-square"></i> Edit
                            </RouterLink>
                            <button type="button" class="btn btn-sm btn-outline-danger" @click="deletePlan(p)"
                                :aria-label="`Delete ${p.name}`">
                                <i class="fa-solid fa-trash-can"></i> Delete
                            </button>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </DataCard>

    <div class="card mt-4" v-if="!loading && !error && trainers.length > 0">
        <div class="card-body">
            <h2 class="h4">Personal trainer</h2>
            <p class="text-secondary">
                Optional. The trainer's fee is added for every month of the plan.
                Example: Quarterly plan with a {{ formatPrice(trainers[0]!.monthlyFee) }} trainer adds
                {{ formatPrice(trainers[0]!.monthlyFee * 3) }}.
            </p>
            <table class="table align-middle mb-0">
                <thead>
                    <tr>
                        <th scope="col">Trainer</th>
                        <th scope="col">Specialty</th>
                        <th scope="col" class="text-end">Fee / month</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="t in trainers" :key="t.id">
                        <td><RouterLink :to="`/trainers/${t.id}`">{{ t.name }}</RouterLink></td>
                        <td>{{ t.specialty }}</td>
                        <td class="text-end text-nowrap">{{ formatPrice(t.monthlyFee) }}</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>
