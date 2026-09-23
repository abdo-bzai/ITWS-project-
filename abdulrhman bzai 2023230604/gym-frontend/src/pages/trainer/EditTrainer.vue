<script lang="ts" setup>
import FormCard from '@/components/FormCard.vue'
import Loading from '@/components/Loading.vue'
import type { TrainerModel } from '@/models/trainer.model'
import { DataService } from '@/services/data.service'
import { getErrorMessage, showError, showSuccess } from '@/utils'
import { onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import TrainerFields from './TrainerFields.vue'

const route = useRoute()
const router = useRouter()
const id = Number(route.params.id)

const trainer = ref<Partial<TrainerModel>>()
const error = ref('')
const saving = ref(false)

const crumbs = [
    { title: 'Trainers', to: '/trainers' },
    { title: 'Edit' }
]

onMounted(async () => {
    try {
        trainer.value = (await DataService.getTrainerById(id)).data
    } catch (e) {
        error.value = getErrorMessage(e)
    }
})

async function save() {
    saving.value = true
    try {
        await DataService.updateTrainer(id, trainer.value!)
        showSuccess('Changes saved')
        router.push('/trainers')
    } catch (e) {
        showError(e)
    } finally {
        saving.value = false
    }
}
</script>

<template>
    <div class="alert alert-danger crud-wrapper" v-if="error">{{ error }}</div>
    <FormCard :title="`Edit ${trainer.name || 'trainer'}`" :crumbs="crumbs" btn-text="Save changes"
        :saving="saving" @submit="save" v-else-if="trainer">
        <TrainerFields v-model="trainer" />
    </FormCard>
    <Loading v-else />
</template>
