import React from "react";

const HungryContext = React.createContext({ test: "test" });

export const HungryProvider = HungryContext.Provider;
export const HungryConsumer = HungryContext.Consumer;
