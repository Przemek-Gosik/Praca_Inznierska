export interface Lesson{
    idFastWritingLesson?: number
    idFastWritingCourse?: number
    name: string
    score?: number
    number: number
    generatedCharacters: string
    text: string[]
}


export interface Module{
    idFastWritingModule?: number
    name: string
    number: number
    lessons: Lesson[]

}

export interface WritingResult{
    idFastWritingCourse?: number
    idFastWritingLesson: number
    numberOfAttempts?: number
    startTime: string
    score: number
    typedLetters: number
}