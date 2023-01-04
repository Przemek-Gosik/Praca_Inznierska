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

export interface WritingText{
    idFastWritingText?: number
    level: string
    text: string
    title: string 
}

export interface WritingCourseResult{
    idFastWritingCourse?: number
    idFastWritingLesson: number
    time: number
    numberOfAttempts?: number
    startTime: string
    score: number
    numberOfTypedLetters: number
}

export interface WritingTestResult{
    idFastWritingTest?: number
    typedText: string
    score: number
    startTime: string
    time: number
    idText?: number
}