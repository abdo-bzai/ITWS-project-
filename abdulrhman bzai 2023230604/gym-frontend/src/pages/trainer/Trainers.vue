<script lang="ts" setup>
import DataCard from '@/components/DataCard.vue'
import Loading from '@/components/Loading.vue'
import type { MemberModel } from '@/models/member.model'
import type { TrainerModel } from '@/models/trainer.model'
import { DataService } from '@/services/data.service'
import { formatPrice, getErrorMessage, showConfirm, showError, showSuccess } from '@/utils'
import { onMounted, ref } from 'vue'

const trainers = ref<TrainerModel[]>([])
const members = ref<MemberModel[]>([])
const loading = ref(true)
const error = ref('')

function memberCount(trainer: TrainerModel) {
    return members.value.filter(m => m.trainer?.id === trainer.id).length
}

async function loadData() {
    try {
        const [t, m] = await Promise.all([DataService.getTrainers(), DataService.getMembers()])
        trainers.value = t.data
        members.value = m.data
    } catch (e) {
        error.value = getErrorMessage(e)
    } finally {
        loading.value = false
    }
}

async function deleteTrainer(trainer: TrainerModel) {
    const confirmed = await showConfirm(`Delete ${trainer.name}?`, 'Delete trainer')
    if (!confirmed) return

    try {
        await DataService.deleteTrainer(trainer.id)
        showSuccess('Trainer deleted')
        loadData()
    } catch (e) {
        showError(e, 'Not deleted')
    }
}

onMounted(() => loadData())
</script>

<template>
    <DataCard title="Trainers" lead="Personal trainers and their monthly fee. A trainer who still has members can't be deleted."
        btn-title="Add trainer" btn-to="/trainers/new">
        <Loading v-if="loading" />
        <div class="alert alert-danger mb-0" v-else-if="error">{{ error }}</div>
        <div class="empty-state" v-else-if="trainers.length === 0">
            No trainers yet. Use "Add trainer" to create the first one.
        </div>
        <table class="table align-middle mb-0" v-else>
            <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Name</th>
                    <th scope="col">Specialty</th>
                    <th scope="col">Phone</th>
                    <th scope="col" class="text-end">Fee / month</th>
                    <th scope="col">Members</th>
                    <th scope="col" class="text-end">Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="t in trainers" :key="t.id">
                    <td>{{ t.id }}</td>
                    <td class="fw-semibold">{{ t.name }}</td>
                    <td>{{ t.specialty }}</td>
                    <td>{{ t.phone || '-' }}</td>
                    <td class="text-end text-nowrap">{{ formatPrice(t.monthlyFee) }}</td>
                    <td>
                        <RouterLink :to="`/members?trainer=${t.id}`">{{ memberCount(t) }}</RouterLink>
                    </td>
                    <td class="text-end">
                        <div class="btn-group">
                            <RouterLink class="btn btn-sm btn-outline-primary" :to="`/trainers/${t.id}`"
                                :aria-label="`Edit ${t.name}`">
                                <i class="fa-solid fa-pen-to-square"></i> Edit
                            </RouterLink>
                            <button type="button" class="btn btn-sm btn-outline-danger" @click="deleteTrainer(t)"
                                :aria-label="`Delete ${t.name}`">
                                <i class="fa-solid fa-trash-can"></i> Delete
                            </button>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </DataCard>
</template>
