<script lang="ts" setup>
import Loading from '@/components/Loading.vue'
import type { MemberModel } from '@/models/member.model'
import type { TrainerModel } from '@/models/trainer.model'
import { DataService } from '@/services/data.service'
import { getErrorMessage, statusClass } from '@/utils'
import { computed, onMounted, ref } from 'vue'

const trainers = ref<TrainerModel[]>([])
const members = ref<MemberModel[]>([])
const loading = ref(true)
const error = ref('')

// The relation in action: find every member whose trainer is this trainer
function membersOf(trainer: TrainerModel) {
    return members.value.filter(m => m.trainer?.id === trainer.id)
}

const trainingAlone = computed(() => members.value.filter(m => m.trainer === null))

function countStatus(status: string) {
    return members.value.filter(m => m.status === status).length
}

onMounted(async () => {
    try {
        const [t, m] = await Promise.all([DataService.getTrainers(), DataService.getMembers()])
        trainers.value = t.data
        members.value = m.data
    } catch (e) {
        error.value = getErrorMessage(e)
    } finally {
        loading.value = false
    }
})
</script>

<template>
    <div class="d-flex flex-wrap justify-content-between align-items-end gap-3 mb-4">
        <div>
            <h1 class="page-title">Roster</h1>
            <p class="page-lead mb-0" v-if="!loading && !error">
                {{ members.length }} members:
                <RouterLink to="/members?status=Active">{{ countStatus('Active') }} active</RouterLink>,
                <RouterLink to="/members?status=Expiring soon">{{ countStatus('Expiring soon') }} expiring soon</RouterLink>,
                <RouterLink to="/members?status=Expired">{{ countStatus('Expired') }} expired</RouterLink>.
            </p>
        </div>
        <div class="d-flex gap-2">
            <RouterLink class="btn btn-outline-secondary" to="/trainers/new">
                <i class="fa-solid fa-plus"></i> Add trainer
            </RouterLink>
            <RouterLink class="btn btn-primary" to="/members/new">
                <i class="fa-solid fa-plus"></i> Add member
            </RouterLink>
        </div>
    </div>

    <Loading v-if="loading" />
    <div class="alert alert-danger" v-else-if="error">{{ error }}</div>
    <div class="card empty-state" v-else-if="members.length === 0 && trainers.length === 0">
        <p>No trainers or members yet. Start by adding a member.</p>
        <RouterLink class="btn btn-primary mx-auto" to="/members/new">Add member</RouterLink>
    </div>

    <div class="roster" v-else>
        <section class="roster-trainer" v-for="t in trainers" :key="t.id">
            <div class="d-flex justify-content-between align-items-start gap-2">
                <div>
                    <h3>{{ t.name }}</h3>
                    <div class="specialty">{{ t.specialty }}</div>
                </div>
                <RouterLink class="btn btn-sm btn-outline-secondary text-nowrap" :to="`/members?trainer=${t.id}`"
                    :aria-label="`See members of ${t.name}`">
                    {{ membersOf(t).length }} <i class="fa-solid fa-users"></i>
                </RouterLink>
            </div>
            <ul v-if="membersOf(t).length > 0">
                <li v-for="m in membersOf(t)" :key="m.id">
                    <RouterLink :to="`/members/${m.id}`" class="text-decoration-none">{{ m.name }}</RouterLink>
                    <span :class="statusClass(m.status)">{{ m.status }}</span>
                </li>
            </ul>
            <p class="text-secondary mt-3 mb-0" v-else>No members assigned yet.</p>
        </section>

        <section class="roster-trainer roster-alone" v-if="trainingAlone.length > 0">
            <div class="d-flex justify-content-between align-items-start gap-2">
                <div>
                    <h3>Training alone</h3>
                    <div class="specialty">Members without a trainer</div>
                </div>
                <RouterLink class="btn btn-sm btn-outline-secondary text-nowrap" to="/members?trainer=none"
                    aria-label="See members training alone">
                    {{ trainingAlone.length }} <i class="fa-solid fa-users"></i>
                </RouterLink>
            </div>
            <ul>
                <li v-for="m in trainingAlone" :key="m.id">
                    <RouterLink :to="`/members/${m.id}`" class="text-decoration-none">{{ m.name }}</RouterLink>
                    <span :class="statusClass(m.status)">{{ m.status }}</span>
                </li>
            </ul>
        </section>
    </div>
</template>
