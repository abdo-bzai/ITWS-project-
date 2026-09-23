import Swal from "sweetalert2"

const swalClasses = {
    confirmButton: 'btn btn-primary mx-1',
    cancelButton: 'btn btn-outline-secondary mx-1'
}

// "2026-09-22" -> "22.09.2026."
export function formatDate(iso: string | null | undefined) {
    if (!iso) return '-'
    const [y, m, d] = iso.substring(0, 10).split('-')
    return `${d}.${m}.${y}.`
}

// Today's date in the format a date input expects: "2026-09-22"
export function todayIso() {
    const now = new Date()
    const m = String(now.getMonth() + 1).padStart(2, '0')
    const d = String(now.getDate()).padStart(2, '0')
    return `${now.getFullYear()}-${m}-${d}`
}

// 5000 -> "5.000 RSD"
export function formatPrice(amount: number | null | undefined) {
    if (amount == null) return '-'
    return String(Math.round(amount)).replace(/\B(?=(\d{3})+(?!\d))/g, '.') + ' RSD'
}

// "1 month", "3 months"
export function formatMonths(months: number) {
    return months === 1 ? '1 month' : `${months} months`
}

// Same rule as Java's LocalDate.plusMonths: 31.01. + 1 month = 28.02. (or 29.02.)
export function addMonths(iso: string, months: number) {
    const [y, m, d] = iso.split('-').map(Number) as [number, number, number]
    const total = (m - 1) + months
    const year = y + Math.floor(total / 12)
    const month = total % 12
    const lastDay = new Date(year, month + 1, 0).getDate()
    const day = Math.min(d, lastDay)
    return `${year}-${String(month + 1).padStart(2, '0')}-${String(day).padStart(2, '0')}`
}

// CSS class for a member status badge
export function statusClass(status: string) {
    if (status === 'Expired') return 'status status-expired'
    if (status === 'Expiring soon') return 'status status-soon'
    return 'status status-active'
}

// CSS class for a plan badge (colored like weight plates: longer plan = heavier plate)
export function planClass(months: number) {
    if ([1, 3, 6, 12].includes(months)) return `plate plate-${months}`
    return 'plate plate-other'
}

// Reads the "message" our Spring Boot ErrorAdvice sends back
export function getErrorMessage(error: any) {
    if (error?.response?.data?.message) return error.response.data.message
    if (error?.code === 'ERR_NETWORK') return "Can't reach the server. Make sure the backend is running on port 7000."
    return 'Something went wrong. Please try again.'
}

export async function showConfirm(title: string, confirmText: string) {
    const result = await Swal.fire({
        title,
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: confirmText,
        cancelButtonText: 'Cancel',
        buttonsStyling: false,
        customClass: swalClasses
    })
    return result.isConfirmed
}

export function showSuccess(title: string) {
    return Swal.fire({
        title,
        icon: 'success',
        timer: 1400,
        showConfirmButton: false
    })
}

export function showError(error: any, title = 'Not saved') {
    return Swal.fire({
        title,
        text: getErrorMessage(error),
        icon: 'error',
        confirmButtonText: 'OK',
        buttonsStyling: false,
        customClass: swalClasses
    })
}
