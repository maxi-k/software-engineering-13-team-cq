export const capitalizeString = (toCapitalize: string): string => (
  toCapitalize.charAt(0).toUpperCase() + toCapitalize.slice(1).toLowerCase()
)
