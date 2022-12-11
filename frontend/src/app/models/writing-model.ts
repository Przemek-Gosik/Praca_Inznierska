export interface Lesson{
    idFastWritingLesson?: number
    idFastWritingCourse?: number
    name: string
    score?: number
    number: number
    generatedCharacters: string
    test: string[]
}


export interface Module{
    idFastWritingModule?: number
    name: "string"
    number: number
    lessons: Lesson[]

}