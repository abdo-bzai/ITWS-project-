<script lang="ts" setup>
import FormCard from '@/components/FormCard.vue'
import type { PlanModel } from '@/models/plan.model'
import { DataService } from '@/services/data.service'
import { showError, showSuccess } from '@/utils'
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import PlanFields from './PlanFields.vue'

const router = useRouter()
const saving = ref(false)
const plan = ref<Partial<PlanModel>>({ name: '', durationMonths: 1, price: 5000 })

const crumbs = [
    { title: 'Price list', to: '/plans' },
    { title: 'Add' }
]

async function save() {
    saving.value = true
    try {
        await DataService.createPlan(plan.value)
        showSuccess('Plan added')
        router.push('/plans')
    } catch (e) {
        showError(e)
    } finally {
        saving.value = false
    }
}
</script>

<template>
    <FormCard title="Add plan" :crumbs="crumbs" btn-text="Add plan" :saving="saving" @submit="save">
        <PlanFields v-model="plan" />
    </FormCard>
</template>
