<script lang="ts" setup>
import FormCard from '@/components/FormCard.vue'
import Loading from '@/components/Loading.vue'
import type { PlanModel } from '@/models/plan.model'
import { DataService } from '@/services/data.service'
import { getErrorMessage, showError, showSuccess } from '@/utils'
import { onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import PlanFields from './PlanFields.vue'

const route = useRoute()
const router = useRouter()
const id = Number(route.params.id)

const plan = ref<Partial<PlanModel>>()
const error = ref('')
const saving = ref(false)

const crumbs = [
    { title: 'Price list', to: '/plans' },
    { title: 'Edit' }
]

onMounted(async () => {
    try {
        plan.value = (await DataService.getPlanById(id)).data
    } catch (e) {
        error.value = getErrorMessage(e)
    }
})

async function save() {
    saving.value = true
    try {
        await DataService.updatePlan(id, plan.value!)
        showSuccess('Changes saved')
        router.push('/plans')
    } catch (e) {
        showError(e)
    } finally {
        saving.value = false
    }
}
</script>

<template>
    <div class="alert alert-danger crud-wrapper" v-if="error">{{ error }}</div>
    <FormCard :title="`Edit ${plan.name || 'plan'}`" :crumbs="crumbs" btn-text="Save changes" :saving="saving"
        @submit="save" v-else-if="plan">
        <p class="text-secondary small">
            New prices apply to members who sign up or are edited after this change.
            Members who already paid keep their price.
        </p>
        <PlanFields v-model="plan" />
    </FormCard>
    <Loading v-else />
</template>
