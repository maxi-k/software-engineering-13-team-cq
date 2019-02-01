export const environment: string = process.env.NODE_ENV

export const isDevelopment = environment === 'development'
export const isProduction = environment === 'production'
export const isStaging = environment === 'staging'
export const isTest = environment === 'test'
