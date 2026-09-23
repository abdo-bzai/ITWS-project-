<script lang="ts" setup>
import FormCard from '@/components/FormCard.vue'
import Loading from '@/components/Loading.vue'
import type { MemberFormModel } from '@/models/member.model'
import type { PlanModel } from '@/models/plan.model'
import type { TrainerModel } from '@/models/trainer.model'
import { DataService } from '@/services/data.service'
import { getErrorMessage, showError, showSuccess } from '@/utils'
import { onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import MemberFields from './MemberFields.vue'

const route = useRoute()
const router = useRouter()
const id = Number(route.params.id)

const member = ref<MemberFormModel>()
const plans = ref<PlanModel[]>([])
const trainers = ref<TrainerModel[]>([])
const error = ref('')
const saving = ref(false)

const crumbs = [
    { title: 'Members', to: '/members' },
    { title: 'Edit' }
]

onMounted(async () => {
    try {
        const [m, p, t] = await Promise.all([
            DataService.getMemberById(id),
            DataService.getPlans(),
            DataService.getTrainers()
        ])
        plans.value = p.data
        trainers.value = t.data
        // Convert the member from the backend into the form shape (objects -> ids)
        member.value = {
            name: m.data.name,
            email: m.data.email,
            startDate: m.data.startDate,
            planId: m.data.plan.id,
            trainerId: m.data.trainer?.id ?? null
        }
    } catch (e) {
        error.value = getErrorMessage(e)
    }
})

async function save() {
    saving.value = true
    try {
        await DataService.updateMember(id, member.value!)
        showSuccess('Changes saved')
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
    <FormCard :title="`Edit ${member.name || 'member'}`" :crumbs="crumbs" btn-text="Save changes" :saving="saving"
        @submit="save" v-else-if="member">
        <p class="text-secondary small">
            To renew a membership, set a new start date. The end date and price are recalculated when you save.
        </p>
        <MemberFields v-model="member" :plans="plans" :trainers="trainers" />
    </FormCard>
    <Loading v-else />
</template>
