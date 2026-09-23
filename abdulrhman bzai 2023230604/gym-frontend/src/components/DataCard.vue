<script lang="ts" setup>
export interface DataCardProps {
    title: string
    lead?: string
    btnTitle?: string
    btnTo?: string
}

const props = defineProps<DataCardProps>()
</script>

<template>
    <div class="d-flex flex-wrap justify-content-between align-items-end gap-3 mb-3">
        <div>
            <h1 class="page-title">{{ props.title }}</h1>
            <p class="page-lead mb-0" v-if="props.lead">{{ props.lead }}</p>
        </div>
        <RouterLink class="btn btn-primary" :to="props.btnTo" v-if="props.btnTitle && props.btnTo">
            <i class="fa-solid fa-plus"></i> {{ props.btnTitle }}
        </RouterLink>
    </div>
    <div class="card">
        <div class="card-body overflow-auto">
            <slot></slot>
        </div>
    </div>
</template>
