<script lang="ts" setup>
import type { PlanModel } from '@/models/plan.model'
import { formatPrice } from '@/utils'

// Shared form fields used by both NewPlan and EditPlan
const plan = defineModel<Partial<PlanModel>>({ required: true })
</script>

<template>
    <div class="mb-3">
        <label for="name" class="form-label">Plan name</label>
        <input type="text" class="form-control" id="name" v-model="plan.name" placeholder="e.g. Quarterly" required>
    </div>
    <div class="row">
        <div class="col-sm-6 mb-3">
            <label for="duration" class="form-label">Duration (months)</label>
            <input type="number" class="form-control" id="duration" v-model.number="plan.durationMonths" min="1"
                required>
        </div>
        <div class="col-sm-6 mb-3">
            <label for="price" class="form-label">Price (RSD)</label>
            <input type="number" class="form-control" id="price" v-model.number="plan.price" min="0" step="100"
                required>
        </div>
    </div>
    <p class="text-secondary small mb-0" v-if="plan.price && plan.durationMonths">
        That's {{ formatPrice(plan.price / plan.durationMonths) }} per month.
    </p>
</template>
