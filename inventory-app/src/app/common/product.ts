import { User } from "./user";

export class Product {

    constructor(
        public id: number,
        public name: string,
        public quantity: number,
        public entryDate: Date,
        public registeredBy?: User,
        public lastModifiedBy?: User
    ) {}

}
