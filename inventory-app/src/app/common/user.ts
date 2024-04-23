export class User {
    
    constructor(
        public name: string,
        public age: number,
        public position: { id: number, name: string },
        public entryDate: string,
        public id: number,
    ) {}
}
