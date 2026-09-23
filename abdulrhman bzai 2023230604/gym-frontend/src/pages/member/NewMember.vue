<script lang="ts" setup>
import FormCard from '@/components/FormCard.vue'
import Loading from '@/components/Loading.vue'
import type { MemberFormModel } from '@/models/member.model'
import type { PlanModel } from '@/models/plan.model'
import type { TrainerModel } from '@/models/trainer.model'
import { DataService } from '@/services/data.service'
import { getErrorMessage, showError, showSuccess, todayIso } from '@/utils'
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import MemberFields from './MemberFields.vue'

const router = useRouter()
const plans = ref<PlanModel[]>()
const trainers = ref<TrainerModel[]>([])
const error = ref('')
const saving = ref(false)

const member = ref<MemberFormModel>({
    name: '',
    email: '',
    startDate: todayIso(),
    planId: null,
    trainerId: null
})

const crumbs = [
    { title: 'Members', to: '/members' },
    { title: 'Add' }
]

onMounted(async () => {
    try {
        const [p, t] = await Promise.all([DataService.getPlans(), DataService.getTrainers()])
        plans.value = p.data
        trainers.value = t.data
        // Pre-select the first (shortest) plan
        member.value.planId = p.data[0]?.id ?? null
    } catch (e) {
        error.value = getErrorMessage(e)
    }
})

async function save() {
    saving.value = true
    try {
        await DataService.createMember(member.value)
        showSuccess('Member added')
        router.push('/members')
    } catch (e) {
        showError(e)
    } finally {
        saving.value = false
    }
}
</script>

<template>
    <div class="alert alert-danger crud-wrapper" v-if="error">{{ error }}</div>
    <Loading v-else-if="!plans" />
    <div class="card crud-wrapper empty-state" v-else-if="plans.length === 0">
        <p>Every member needs a plan. Add a plan to the price list first.</p>
        <RouterLink class="btn btn-primary mx-auto" to="/plans/new">Add plan</RouterLink>
    </div>
    <FormCard title="Add member" :crumbs="crumbs" btn-text="Add member" :saving="saving" @submit="save" v-else>
        <MemberFields v-model="member" :plans="plans" :trainers="trainers" />
    </FormCard>
</template>
