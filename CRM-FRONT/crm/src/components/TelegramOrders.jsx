import React, { useState } from "react";
import { Eye, MessageCircle } from "lucide-react";

const mockOrders = [
    {
        id: 1,
        customerName: "Sara El Idrissi",
        contact: "@sara_jewels",
        material: "material 01",
        occasion: "Ocassion 01",
        category: "category 01",
        design: "Delicate bracelet with floral engraving",
        color: "Rose Gold",
        description: "Looking for a thin rose gold bracelet with flower patterns and a diamond centerpiece.",
        status: "PENDING",
    },
    {
        id: 2,
        customerName: "Omar Lahrichi",
        material: "material 01",
        occasion: "Ocassion 01",
        category: "category 01",
        contact: "@omar_customs",
        design: "Bold ring with Berber patterns",
        color: "Silver",
        description: "Chunky silver ring engraved with Amazigh symbols. Prefer brushed finish.",
        status: "PENDING",
    },
];

const TelegramOrders = () => {
    const [expandedId, setExpandedId] = useState(null);

    const toggleDetails = (id) => {
        setExpandedId(expandedId === id ? null : id);
    };

    return (
        <div className="bg-gray-100 dark:bg-gray-900 min-h-screen p-6 transition-colors">
            <div className="flex items-center gap-2 mb-6">
                <MessageCircle className="text-blue-500 dark:text-blue-400" size={28} />
                <h1 className="text-2xl font-bold text-gray-800 dark:text-white">
                    Telegram Jewelry Orders
                </h1>
            </div>

            <div className="grid gap-5">
                {mockOrders.map((order) => (
                    <div
                        key={order.id}
                        className="bg-white dark:bg-gray-800 shadow-md rounded-lg p-5 transition-all"
                    >
                        <div className="flex flex-col md:flex-row md:justify-between md:items-center">
                            <div className="space-y-1">
                                <p className="text-lg font-semibold text-gray-900 dark:text-white">
                                    {order.customerName}
                                </p>
                                <p className="text-sm text-gray-500 dark:text-gray-400">{order.contact}</p>
                                <p className="text-sm">
                                    🎨 <strong>Color:</strong> {order.color}
                                </p>
                                <p className="text-sm">
                                    💡 <strong>Design:</strong> {order.design}
                                </p>
                                <p className="text-sm">
                                    💡 <strong>Material:</strong> {order.material}
                                </p>
                                <p className="text-sm">
                                    💡 <strong>Category:</strong> {order.category}
                                </p>
                                <p className="text-sm">
                                    💡 <strong>Occasion:</strong> {order.occasion}
                                </p>
                            </div>

                            <div className="mt-3 md:mt-0 text-right">
                                <span
                                    className={`inline-block px-3 py-1 rounded-full text-xs font-bold ${order.status === "PENDING"
                                        ? "bg-yellow-400 text-black"
                                        : "bg-green-500 text-white"
                                        }`}
                                >
                                    {order.status}
                                </span>
                                <br />
                                <button
                                    onClick={() => toggleDetails(order.id)}
                                    className="mt-2 text-sm px-4 py-1 bg-blue-600 hover:bg-blue-700 text-white rounded-md"
                                >
                                    {expandedId === order.id ? "Hide Details" : "View Details"}
                                </button>
                            </div>
                        </div>

                        {expandedId === order.id && (
                            <div className="mt-4 border-t pt-4 text-sm text-gray-700 dark:text-gray-300">
                                <p className="font-semibold mb-1">📝 Description:</p>
                                <p>{order.description}</p>
                            </div>
                        )}
                    </div>
                ))}
            </div>
        </div>
    );
}

export default TelegramOrders;