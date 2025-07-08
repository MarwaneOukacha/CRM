import { createBrowserRouter, RouterProvider } from "react-router-dom";

import { ThemeProvider } from "@/contexts/theme-context";

import Layout from "@/routes/layout";
import DashboardPage from "@/routes/dashboard/page";
import Customers from "./components/materials";
import CreateCustomer from "./components/createCustomer";
import Bookings from "./components/bookings";
import  Settings  from "./components/settings";
import CalendarComponent from "./components/calendar";
import Materials from "./components/materials";
import Designers from "./components/designers";
import Colors from "./components/Colors";
import DailySales from "./components/DailySales";
import Category from "./components/Category";
import Occasions from "./components/Occasions";
import Carats from "./components/Carats";
import Products from "./components/Products";
import ProductDetail from "./components/ProductDetail";

function App() {
    const router = createBrowserRouter([
        {
            path: "/",
            element: <Layout />,
            children: [
                {
                    index: true,
                    element: <DashboardPage />,
                },
                {
                    path: "calendar",
                    element: <CalendarComponent/>,
                },
                {
                    path: "reports",
                    element: <h1 className="title">Reports</h1>,
                },
                {
                    path: "materials",
                    element: <Materials/>,
                },
                {
                    path: "designers",
                    element: <Designers/>,
                },
                {
                    path: "colors",
                    element: <Colors/>,
                },
                {
                    path: "daily-Sales",
                    element: <DailySales/>,
                },
                {
                    path: "categorys",
                    element: <Category/>,
                },
                {
                    path: "Occasions",
                    element: <Occasions/>,
                },
                {
                    path: "carats",
                    element: <Carats/>,
                },
                
                {
                    path: "settings",
                    element: <Settings/>,
                },
                {
                    path: "products",
                    element: <Products/>,
                },
                {
                    path: "products/:id",
                    element: <ProductDetail/>,
                },
                
            ],
        },
    ]);

    return (
        <ThemeProvider storageKey="theme">
            <RouterProvider router={router} />
        </ThemeProvider>
    );
}

export default App;