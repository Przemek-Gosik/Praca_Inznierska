export interface Lesson{
    idWritingLesson?: number
    idWritingLessonResult?: number
    name: string
    score?: number
    number: number
    generatedCharacters: string
    text?: string[]
}


export interface Module{
    idWritingModule?: number
    name: string
    number: number
    lessons: Lesson[]

}

export interface WritingText{
    idWritingText?: number
    level: string
    text: string
    title: string 
}

export interface WritingLessonResult{
    idWritingLessonResult?: number
    idWritingLesson: number
    time: number
    numberOfAttempts?: number
    startTime: string
    score: number
    numberOfTypedLetters: number
}

export interface WritingTextResult{
    idWritingTextResult?: number
    typedText: string
    score: number
    startTime: string
    time: number
    idText?: number
    title?: string
}