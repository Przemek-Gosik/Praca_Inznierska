export interface MemorizngResult{
    idMemorizing?: number
    level: string
    score: number
    startTime: string
    type: string
}

export interface MemorizingNumbers{
    randomNumbers: number[]
    shuffledRandomNumbers: number[]
}

export interface CardDetails{
    imageName: string
    state: 'none' | 'chosen' | 'matched'
}