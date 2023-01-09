export interface Table{
    column : number[]
}

export interface ReadingResult{
    idFastReadingText?: number,
    idReadingResult?: number,
    level: string,
    score: number,
    startTime: string,
    time: number,
    type: string
}