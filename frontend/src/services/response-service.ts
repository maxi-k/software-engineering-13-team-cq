import { FetchError } from '@/model'

export const handledStatus: {[key: number]: string} = {
  404: 'notFound',
  401: 'unauthorized',
  400: 'badRequest',
  500: 'serverError'
}

export const ensureResponseStatus = (response: Response): void => {
  if (response.status in handledStatus) {
    throw { status: response.status }
  }
}

export const messageFromError = (error: FetchError): string => {
  const prefix = 'cns.message.fetch.error.'
  switch (typeof error) {
    case 'object':
      if ('status' in error) {
        if (error.status in handledStatus) {
          return prefix + handledStatus[error.status]
        }
      }
  }
  // string | boolean
  return prefix + 'default'
}
