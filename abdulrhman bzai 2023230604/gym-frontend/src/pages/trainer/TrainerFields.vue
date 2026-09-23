<script lang="ts" setup>
import type { TrainerModel } from '@/models/trainer.model'

// Shared form fields used by both NewTrainer and EditTrainer
const trainer = defineModel<Partial<TrainerModel>>({ required: true })
</script>

<template>
    <div class="mb-3">
        <label for="name" class="form-label">Full name</label>
        <input type="text" class="form-control" id="name" v-model="trainer.name" required>
    </div>
    <div class="mb-3">
        <label for="specialty" class="form-label">Specialty</label>
        <input type="text" class="form-control" id="specialty" v-model="trainer.specialty" list="specialties"
            placeholder="e.g. Strength, Yoga, Cardio" required>
        <datalist id="specialties">
            <option value="Strength"></option>
            <option value="Yoga"></option>
            <option value="Cardio"></option>
            <option value="CrossFit"></option>
            <option value="Pilates"></option>
        </datalist>
    </div>
    <div class="mb-3">
        <label for="fee" class="form-label">Monthly fee (RSD)</label>
        <input type="number" class="form-control" id="fee" v-model.number="trainer.monthlyFee" min="0" step="100"
            aria-describedby="feeHelp" required>
        <div id="feeHelp" class="form-text">Added to the member's plan price for every month of the plan.</div>
    </div>
    <div class="mb-1">
        <label for="phone" class="form-label">Phone <span class="text-secondary">(optional)</span></label>
        <input type="tel" class="form-control" id="phone" v-model="trainer.phone">
    </div>
</template>
