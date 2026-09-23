<script lang="ts" setup>
export interface FormCardProps {
    title: string
    crumbs: {
        title: string
        to?: string
    }[]
    btnText: string
    saving?: boolean
}

const props = defineProps<FormCardProps>()
const emit = defineEmits<{ submit: [] }>()
</script>

<template>
    <div class="crud-wrapper">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item">
                    <RouterLink to="/">Roster</RouterLink>
                </li>
                <template v-for="crumb in props.crumbs" :key="crumb.title">
                    <li class="breadcrumb-item" v-if="crumb.to">
                        <RouterLink :to="crumb.to">{{ crumb.title }}</RouterLink>
                    </li>
                    <li class="breadcrumb-item active" aria-current="page" v-else>
                        {{ crumb.title }}
                    </li>
                </template>
            </ol>
        </nav>
        <form class="card" @submit.prevent="emit('submit')">
            <div class="card-body">
                <h1 class="page-title mb-3">{{ props.title }}</h1>
                <slot></slot>
            </div>
            <div class="card-footer d-flex gap-2">
                <button type="submit" class="btn btn-primary" :disabled="props.saving">
                    <i class="fa-solid fa-floppy-disk"></i> {{ props.btnText }}
                </button>
                <RouterLink class="btn btn-outline-secondary" :to="props.crumbs[0]?.to ?? '/'">Cancel</RouterLink>
            </div>
        </form>
    </div>
</template>
