import { type Directive, type DirectiveBinding } from 'vue'
import iframeResize from 'iframe-resizer/js/iframeResizer'

interface ResizableHTMLElement extends HTMLElement {
    iFrameResizer?: {
        removeListeners: () => void
    }
}

const resize: Directive = {
    mounted(el: HTMLElement, binding: DirectiveBinding) {
        const options = binding.value || {}

        el.addEventListener('load', () => iframeResize(options, el))
    },
    unmounted(el: HTMLElement) {
        const resizableEl = el as ResizableHTMLElement

        if (resizableEl.iFrameResizer) {
            resizableEl.iFrameResizer.removeListeners()
        }
    },
}

export default resize