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
    name: "string"
    number: number
    lessons: Lesson[]

}

export interface Result{
    idFastWritingCourse?: number
    startTime: number
    score: number
    typedLetters: number
}