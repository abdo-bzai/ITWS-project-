<script lang="ts" setup>
import FormCard from '@/components/FormCard.vue'
import type { TrainerModel } from '@/models/trainer.model'
import { DataService } from '@/services/data.service'
import { showError, showSuccess } from '@/utils'
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import TrainerFields from './TrainerFields.vue'

const router = useRouter()
const saving = ref(false)
const trainer = ref<Partial<TrainerModel>>({ name: '', specialty: '', phone: '', monthlyFee: 2500 })

const crumbs = [
    { title: 'Trainers', to: '/trainers' },
    { title: 'Add' }
]

async function save() {
    saving.value = true
    try {
        await DataService.createTrainer(trainer.value)
        showSuccess('Trainer added')
        router.push('/trainers')
    } catch (e) {
        showError(e)
    } finally {
        saving.value = false
    }
}
</script>

<template>
    <FormCard title="Add trainer" :crumbs="crumbs" btn-text="Add trainer" :saving="saving" @submit="save">
        <TrainerFields v-model="trainer" />
    </FormCard>
</template>
