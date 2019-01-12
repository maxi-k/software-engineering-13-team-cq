import { FetchError } from '@/model'

export const ensureResponseStatus = (response: Response): void => {
  if (response.status === 404) {
    throw { status: 404 }
  }
}

export const messageFromError = (error: FetchError): string => {
  const prefix = 'cns.message.fetch.error.'
  switch (typeof error) {
    case 'object':
      if ('status' in error) {
        switch(error.status) {
          case 400:
            return prefix + 'badRequest'
          case 401:
            return prefix + 'unauthorized'
          case 404:
            return prefix + 'notFound'
        }
      }
  }
  // string | boolean
  return prefix + 'default'
}
