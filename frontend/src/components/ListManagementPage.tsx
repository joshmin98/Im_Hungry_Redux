import * as React from 'react';
import { useState } from 'react';
import { withRouter } from 'react-router-dom';
import {
  Card,
  CardContent,
  Grid,
  Theme,
  createStyles,
  withStyles,
} from '@material-ui/core';
import { DragDropContext, Draggable, Droppable } from 'react-beautiful-dnd';

import Header from './sub-components/Header';

const styles = (theme: Theme) =>
  createStyles({
    root: {
      flexGrow: 1,
    },
    drawerHeader: {
      display: 'flex',
      alignItems: 'center',
      padding: '0 8px',
      ...theme.mixins.toolbar,
      justifyContent: 'flex-start',
    },
    main: {
      marginTop: 10,
    },
    card: {
      marginBottom: 10,
    },
    container: {
      marginLeft: '5%',
      marginRight: '5%',
    },
  });

const ListManagementPage: React.FC<any> = props => {
  const [list, setList]: any = useState([1, 3, 5, 7, 9]);
  const { classes } = props;

  const onDragEnd = (result: any) => {
    if (!result.destination) return;

    const items = reorder(list, result.source.index, result.destination.index);
    setList(items);
  };

  const reorder = (list: any, startIdx: number, endIdx: number) => {
    const result = Array.from(list);
    const [removed] = result.splice(startIdx, 1);
    result.splice(endIdx, 0, removed);
    return result;
  };

  return (
    <div className={classes.root}>
      <Header />
      <div className={classes.container}>
        <h1>{props.location.pathname.slice(7)}</h1>
        <DragDropContext onDragEnd={onDragEnd}>
          <Droppable droppableId="droppable">
            {(provided, snapshot) => (
              <div {...provided.droppableProps} ref={provided.innerRef}>
                {list.map((item: any, idx: any) => (
                  <Draggable key={idx} draggableId={item} index={idx}>
                    {(provided, snapshot) => (
                      <div
                        ref={provided.innerRef}
                        {...provided.draggableProps}
                        {...provided.dragHandleProps}
                      >
                        <Card className={classes.card}>
                          <CardContent>{item}</CardContent>
                        </Card>
                      </div>
                    )}
                  </Draggable>
                ))}
              </div>
            )}
          </Droppable>
        </DragDropContext>
      </div>
    </div>
  );
};

export default withStyles(styles)(withRouter(ListManagementPage));
