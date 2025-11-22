<script setup lang="ts">
import type { RouteFormI } from '@/api/interfaces';
import { formItoRoute } from '@/api/utils';
import { useRoutesStore } from '@/stores/routes';
import { onMounted, onUnmounted, ref, toRefs, watch } from 'vue';

const props = defineProps<{
  isVisible: boolean;

  title?: string;

  closeOnClickOutside?: boolean;

  showCloseButton?: boolean;

  disableEscape?: boolean;

  // какой-то api вызов
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  onConfirm?: (props: any) => Promise<any>;

  routeInitData?: RouteFormI;
}>();

const { routeInitData } = toRefs(props);

const emit = defineEmits<{
  (e: 'update:isVisible', value: boolean): void;
  (e: 'close'): void;
  (e: 'open'): void;
}>();

const close = () => {
  emit('update:isVisible', false);
  emit('close');
};

const createDefaultRoute = (): RouteFormI => ({
  name: '',
  coordinatesX: 0,
  coordinatesY: 0,
  fromX: 0,
  fromY: 0,
  fromZ: 0,
  toX: 0,
  toY: 0,
  toZ: 0,
  distance: 0,
});

const route = ref<RouteFormI>({
  ...createDefaultRoute(),
  ...(props.routeInitData || {}),
});

const formRef = ref<HTMLFormElement>();
const errorRef = ref<string>('');
const routesStore = useRoutesStore();

const handleBackdropClick = (e: MouseEvent) => {
  if (e.target === e.currentTarget && props.closeOnClickOutside !== false) {
    close();
  }
};

const handleKeydown = (e: KeyboardEvent) => {
  if (e.key === 'Escape' && !props.disableEscape) {
    close();
  }
};

onMounted(() => {
  if (!props.disableEscape) {
    document.addEventListener('keydown', handleKeydown);
  }
});

onUnmounted(() => {
  document.removeEventListener('keydown', handleKeydown);
});

watch(routeInitData, (newVal) => {
  route.value = newVal!;
});

const handleConfirmClick = () => {
  if (formRef.value?.checkValidity()) {
    props.onConfirm!(formItoRoute(route.value))
      .then(() => {
        Object.assign(route, createDefaultRoute());
        routesStore.updateRoutes();
        emit('close');
      })
      .catch((err) => {
        errorRef.value = JSON.parse(err.message).message;
      });
  }
};
</script>

<template>
  <div v-if="isVisible" class="popup-overlay" @click="handleBackdropClick">
    <div class="popup-window" role="dialog" aria-modal="true">
      <header v-if="title || showCloseButton" class="popup-header">
        <h3 v-if="title" class="popup-title">{{ title }}</h3>
        <button
          v-if="showCloseButton"
          type="button"
          class="popup-close-btn"
          @click="close"
          aria-label="Close popup"
        >
          &times;
        </button>
      </header>

      <div class="popup-body">
        <form ref="formRef" @submit.prevent class="route-form" id="routeForm">
          <!-- Name -->
          <div class="form-group">
            <label for="name">Route Name</label>
            <input
              id="name"
              v-model="route.name"
              type="text"
              placeholder="Enter route name"
              required
            />
          </div>

          <!-- Coordinates (x, y) -->
          <h3>Coordinates</h3>
          <div class="form-group inline">
            <label for="coordX">X</label>
            <input
              id="coordX"
              v-model.number="route.coordinatesX"
              type="number"
              step="any"
              required
            />
          </div>
          <div class="form-group inline">
            <label for="coordY">Y</label>
            <input
              id="coordY"
              v-model.number="route.coordinatesY"
              type="number"
              step="any"
              required
            />
          </div>

          <!-- From Location (x, y, z) -->
          <h3>From Location</h3>
          <div class="form-group inline">
            <label for="fromX">X</label>
            <input id="fromX" v-model.number="route.fromX" type="number" step="any" required />
          </div>
          <div class="form-group inline">
            <label for="fromY">Y</label>
            <input id="fromY" v-model.number="route.fromY" type="number" step="any" required />
          </div>
          <div class="form-group inline">
            <label for="fromZ">Z</label>
            <input id="fromZ" v-model.number="route.fromZ" type="number" step="any" required />
          </div>

          <!-- To Location (x, y, z) -->
          <h3>To Location</h3>
          <div class="form-group inline">
            <label for="toX">X</label>
            <input id="toX" v-model.number="route.toX" type="number" step="any" required />
          </div>
          <div class="form-group inline">
            <label for="toY">Y</label>
            <input id="toY" v-model.number="route.toY" type="number" step="any" required />
          </div>
          <div class="form-group inline">
            <label for="toZ">Z</label>
            <input id="toZ" v-model.number="route.toZ" type="number" step="any" required />
          </div>

          <!-- Distance -->
          <div class="form-group">
            <label for="distance">Distance</label>
            <input
              id="distance"
              v-model.number="route.distance"
              type="number"
              step="any"
              min="0"
              placeholder="e.g. 10.5"
              required
            />
          </div>
        </form>
        <p style="color: red">{{ errorRef }}</p>
      </div>

      <footer v-if="onConfirm" class="popup-footer">
        <button type="submit" form="routeForm" @click="handleConfirmClick" class="confirm-button">
          Confirm
        </button>
      </footer>
    </div>
  </div>
</template>

<style scoped>
.confirm-button {
  color: #ffffff;
  text-decoration: none;
  font-weight: bold;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  transition: background 0.3s;
  border-width: 0;
  cursor: pointer;
  background-color: rgb(0, 101, 0);
}

.confirm-button:hover {
  background-color: green;
}

.popup-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
  animation: fade-in 0.2s ease-out;
}

.popup-window {
  background: white;
  border-radius: 12px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
  width: 90%;
  max-width: 500px;
  max-height: 90vh;
  overflow-y: auto;
  animation: scale-in 0.2s ease-out;
}

.popup-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 1.5rem;
  border-bottom: 1px solid #eee;
}

.popup-title {
  margin: 0;
  font-size: 1.25rem;
  color: #333;
  font-weight: 600;
}

.popup-close-btn {
  background: none;
  border: none;
  font-size: 1.75rem;
  color: #999;
  cursor: pointer;
  width: 36px;
  height: 36px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  transition:
    background 0.2s,
    color 0.2s;
}

.popup-close-btn:hover,
.popup-close-btn:focus {
  background: #f1f1f1;
  color: #555;
  outline: none;
}

.popup-body {
  padding: 1.5rem;
  color: #555;
  line-height: 1.6;
}

.popup-footer {
  padding: 1rem 1.5rem;
  border-top: 1px solid #eee;
  text-align: right;
  background: #f9f9f9;
  border-bottom-left-radius: 12px;
  border-bottom-right-radius: 12px;
}

@keyframes fade-in {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

@keyframes scale-in {
  from {
    transform: scale(0.9);
    opacity: 0;
  }
  to {
    transform: scale(1);
    opacity: 1;
  }
}

h2,
h3 {
  margin-top: 1rem;
  color: #333;
}

.form-group {
  margin-bottom: 12px;
}

.form-group.inline {
  display: inline-block;
  width: 30%;
  margin-right: 2%;
}

label {
  display: block;
  font-size: 0.9em;
  color: #555;
  margin-bottom: 4px;
}

input[type='text'],
input[type='number'] {
  width: 100%;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1em;
}
</style>
