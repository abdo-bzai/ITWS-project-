<script lang="ts" setup>
import type { MemberFormModel } from '@/models/member.model'
import type { PlanModel } from '@/models/plan.model'
import type { TrainerModel } from '@/models/trainer.model'
import { addMonths, formatDate, formatMonths, formatPrice } from '@/utils'
import { computed } from 'vue'

// Shared form fields used by both NewMember and EditMember
const member = defineModel<MemberFormModel>({ required: true })
const props = defineProps<{ plans: PlanModel[], trainers: TrainerModel[] }>()

const selectedPlan = computed(() => props.plans.find(p => p.id === member.value.planId))
const selectedTrainer = computed(() => props.trainers.find(t => t.id === member.value.trainerId))

// Preview of what the backend will calculate when saving
const trainerCost = computed(() =>
    selectedPlan.value && selectedTrainer.value
        ? selectedTrainer.value.monthlyFee * selectedPlan.value.durationMonths
        : 0
)
const total = computed(() => (selectedPlan.value?.price ?? 0) + trainerCost.value)
const endDate = computed(() =>
    selectedPlan.value && member.value.startDate
        ? addMonths(member.value.startDate, selectedPlan.value.durationMonths)
        : null
)
</script>

<template>
    <div class="mb-3">
        <label for="name" class="form-label">Full name</label>
        <input type="text" class="form-control" id="name" v-model="member.name" required>
    </div>
    <div class="mb-3">
        <label for="email" class="form-label">Email</label>
        <input type="email" class="form-control" id="email" v-model="member.email" required>
    </div>
    <div class="row">
        <div class="col-sm-6 mb-3">
            <label for="plan" class="form-label">Plan</label>
            <!-- Relation 1: the member is linked to the plan chosen here -->
            <select class="form-select" id="plan" v-model="member.planId" required>
                <option :value="null" disabled>Choose a plan</option>
                <option v-for="p in plans" :key="p.id" :value="p.id">{{ p.name }} - {{ formatPrice(p.price) }}</option>
            </select>
        </div>
        <div class="col-sm-6 mb-3">
            <label for="start" class="form-label">Start date</label>
            <input type="date" class="form-control" id="start" v-model="member.startDate" required>
        </div>
    </div>
    <div class="mb-3">
        <label for="trainer" class="form-label">Trainer</label>
        <!-- Relation 2: optional - null means the member trains alone -->
        <select class="form-select" id="trainer" v-model="member.trainerId">
            <option :value="null">No trainer (trains alone)</option>
            <option v-for="t in trainers" :key="t.id" :value="t.id">
                {{ t.name }} ({{ t.specialty }}) + {{ formatPrice(t.monthlyFee) }} / month
            </option>
        </select>
    </div>

    <div class="summary-box" v-if="selectedPlan" aria-live="polite">
        <dl>
            <dt class="fw-normal">{{ selectedPlan.name }} plan ({{ formatMonths(selectedPlan.durationMonths) }})</dt>
            <dd>{{ formatPrice(selectedPlan.price) }}</dd>
            <template v-if="selectedTrainer">
                <dt class="fw-normal">
                    Trainer {{ selectedTrainer.name }}:
                    {{ formatPrice(selectedTrainer.monthlyFee) }} x {{ formatMonths(selectedPlan.durationMonths) }}
                </dt>
                <dd>{{ formatPrice(trainerCost) }}</dd>
            </template>
            <dt class="total">Total</dt>
            <dd class="total">{{ formatPrice(total) }}</dd>
        </dl>
        <p class="mb-0 mt-2 small text-secondary" v-if="endDate">Valid until {{ formatDate(endDate) }}</p>
    </div>
</template>
