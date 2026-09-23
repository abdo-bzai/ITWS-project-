<script lang="ts" setup>
import DataCard from '@/components/DataCard.vue'
import Loading from '@/components/Loading.vue'
import type { MemberModel } from '@/models/member.model'
import type { TrainerModel } from '@/models/trainer.model'
import { DataService } from '@/services/data.service'
import {
    formatDate, formatPrice, getErrorMessage, planClass,
    showConfirm, showError, showSuccess, statusClass
} from '@/utils'
import { computed, onMounted, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

const members = ref<MemberModel[]>([])
const trainers = ref<TrainerModel[]>([])
const loading = ref(true)
const error = ref('')

// Filters are kept in the URL (?trainer=ID or none, ?status=...) so links from other pages work
const trainerFilter = ref<string>(String(route.query.trainer ?? ''))
const statusFilter = ref<string>(String(route.query.status ?? ''))

watch([trainerFilter, statusFilter], ([trainer, status]) => {
    const query: Record<string, string> = {}
    if (trainer) query.trainer = trainer
    if (status) query.status = status
    router.replace({ query })
})

const visibleMembers = computed(() => members.value.filter(m => {
    if (trainerFilter.value === 'none' && m.trainer !== null) return false
    if (trainerFilter.value && trainerFilter.value !== 'none' && String(m.trainer?.id) !== trainerFilter.value) return false
    if (statusFilter.value && m.status !== statusFilter.value) return false
    return true
}))

async function loadData() {
    try {
        const [m, t] = await Promise.all([DataService.getMembers(), DataService.getTrainers()])
        members.value = m.data
        trainers.value = t.data
    } catch (e) {
        error.value = getErrorMessage(e)
    } finally {
        loading.value = false
    }
}

async function deleteMember(member: MemberModel) {
    const confirmed = await showConfirm(`Delete ${member.name}?`, 'Delete member')
    if (!confirmed) return

    try {
        await DataService.deleteMember(member.id)
        showSuccess('Member deleted')
        loadData()
    } catch (e) {
        showError(e, 'Not deleted')
    }
}

onMounted(() => loadData())
</script>

<template>
    <DataCard title="Members" lead="Everyone with a membership: their plan, trainer, and whether it's still valid."
        btn-title="Add member" btn-to="/members/new">
        <Loading v-if="loading" />
        <div class="alert alert-danger mb-0" v-else-if="error">{{ error }}</div>
        <template v-else>
            <div class="row g-3 mb-3">
                <div class="col-sm-6 col-lg-4">
                    <label for="trainerFilter" class="form-label">Trainer</label>
                    <select id="trainerFilter" class="form-select" v-model="trainerFilter">
                        <option value="">All members</option>
                        <option value="none">Training alone</option>
                        <option v-for="t in trainers" :key="t.id" :value="String(t.id)">{{ t.name }}</option>
                    </select>
                </div>
                <div class="col-sm-6 col-lg-4">
                    <label for="statusFilter" class="form-label">Status</label>
                    <select id="statusFilter" class="form-select" v-model="statusFilter">
                        <option value="">Any status</option>
                        <option value="Active">Active</option>
                        <option value="Expiring soon">Expiring soon</option>
                        <option value="Expired">Expired</option>
                    </select>
                </div>
            </div>

            <div class="empty-state" v-if="visibleMembers.length === 0">
                No members match these filters.
            </div>
            <table class="table align-middle mb-0" v-else>
                <thead>
                    <tr>
                        <th scope="col">Name</th>
                        <th scope="col">Plan</th>
                        <th scope="col">Trainer</th>
                        <th scope="col">Valid</th>
                        <th scope="col">Status</th>
                        <th scope="col" class="text-end">Paid</th>
                        <th scope="col" class="text-end">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="m in visibleMembers" :key="m.id">
                        <td>
                            <div class="fw-semibold">{{ m.name }}</div>
                            <div class="small text-secondary">{{ m.email }}</div>
                        </td>
                        <td><span :class="planClass(m.plan.durationMonths)">{{ m.plan.name }}</span></td>
                        <td>{{ m.trainer?.name ?? 'Trains alone' }}</td>
                        <td class="text-nowrap">{{ formatDate(m.startDate) }} - {{ formatDate(m.endDate) }}</td>
                        <td><span :class="statusClass(m.status)">{{ m.status }}</span></td>
                        <td class="text-end text-nowrap">{{ formatPrice(m.totalPrice) }}</td>
                        <td class="text-end">
                            <div class="btn-group">
                                <RouterLink class="btn btn-sm btn-outline-primary" :to="`/members/${m.id}`"
                                    :aria-label="`Edit ${m.name}`" title="Edit">
                                    <i class="fa-solid fa-pen-to-square"></i>
                                </RouterLink>
                                <button type="button" class="btn btn-sm btn-outline-danger" @click="deleteMember(m)"
                                    :aria-label="`Delete ${m.name}`" title="Delete">
                                    <i class="fa-solid fa-trash-can"></i>
                                </button>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </template>
    </DataCard>
</template>
