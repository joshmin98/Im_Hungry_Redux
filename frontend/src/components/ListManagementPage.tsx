import * as React from 'react';
import { useState } from 'react';
import { withRouter } from 'react-router-dom';
import { RouteComponentProps } from 'react-router';
// import { DragDropContext, Droppable, Draggable } from 'react-beautiful-dnd';

const ListManagementPage: React.FC<RouteComponentProps> = ({
  match,
}: RouteComponentProps) => {
  const [list, setList] = useState([1, 3, 5, 7, 9]);

  return (
    <div>
      <h1>{match.params.list}</h1>
      {/* <DragDropContext>
          <Droppable droppableId="droppable">
          {(provided, snapshot) => (
          <div {...provided.droppableProps} ref={provided.innerRef}>
          {list.map((item, idx) => (
          <Draggable key={item} draggableId={item.toString()} index={idx}>
          {(provided, snapshot) => (
          <div
          ref={provided.innerRef}
          {...provided.draggableProps}
          {...provided.dragHandleProps}
          >
          {item}
          </div>
          )}
          </Draggable>
          ))}
          {provided.placeholder}
          </div>
          )}
          </Droppable>
	  </DragDropContext> */}
      {/* asdjasd*/}
    </div>
  );
};

export default withRouter(ListManagementPage);
