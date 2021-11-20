import {Role} from "../enum/Role";

export class User {
    email: string;
    password: string;
    name: string;
    phone: number;
    address: string;
    active: boolean;
    role: string;
    constructor() {
        this.active = true;
        this.role = Role.Customer;
    }
}
